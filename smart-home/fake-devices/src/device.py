import socket
from xmlrpc.client import Boolean
import src.protobuf.iot_pb2 as Messages
import requests
import struct
import logging
import time
import threading
import random

BUFFER_SIZE = 1024
headers = {'Content-type': 'application/x-protobuf'}
logging.basicConfig(level=logging.DEBUG,format='%(asctime)s %(levelname)s %(message)s')

#TODO: send udp-server link to gateway when connecting 
class Device:
    def __init__(self, device_name: str, multicast_group: str, multicast_port: int, udp_server_address: str, udp_server_port: int, sensors: list, actuators: list, sensor_interval: int) -> None:
        self.__multicast_group = multicast_group
        self.__multicast_port = multicast_port
        self.__device_name = device_name
        self.__sensors = sensors
        self.__actuators = actuators
        self.__sensor_interval = sensor_interval
        self.__udp_server_address = udp_server_address
        self.__udp_server_port = udp_server_port

        self.initiate_actuators_state()

        logging.info("starting the device")

        try:
            udp_address = (udp_server_address, udp_server_port)
            self.__udp_connection = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            self.__udp_connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            self.__udp_connection.bind(udp_address)
        except socket.error:
            logging.error(f"could not bind to port, shutting down the device!")
            exit(1)
            return

        (server_address, server_port) = self.wait_for_gateway_info()
        
        join_response = self.join_gateway(server_address, server_port)
        self.__device_id = join_response.id

        logging.info(f"device got the id: {self.__device_id}")

        logging.info("starting generating fake sensor data")

        thread = threading.Thread(target=self.send_sensor_data, args=(server_address, server_port))

        # setting the thread as daemon, to die with its parent
        thread.setDaemon(True)

        thread.start()

        logging.info("starting listing for gateway messages")
        self.listen_to_gateway_messages(server_address, server_port)

    
    def listen_to_gateway_messages(self, server_address, server_port):
        while True:
            message = self.__udp_connection.recv(BUFFER_SIZE).decode()
            logging.info("new message from gateway")
            try:
                actuator_id = int(message)
                actuator_id = actuator_id - 1

                if(actuator_id == -1):
                    logging.info("shutdown message from gateway")
                    self.__udp_connection.close()
                    logging.info("unbinding udp port")
                    exit()
                    return 0

                logging.info(f"toggling actuator {actuator_id}")
                new_value = True if not self.__actuators[actuator_id]['state'] else False
                self.__actuators[actuator_id].update({'state': new_value})
                self.send_sensor_data_once(server_address, server_port)
            except Exception:
                logging.error("invalid message from gateway")
                pass


    def initiate_actuators_state(self):
        for actuator in self.__actuators:
            actuator.update({'state': False})


    def get_measure(self, min: float, max: float):    
        return round(random.uniform(min, max), 2)


    def send_sensor_data_once(self, server_address, server_port):
        logging.info("sending data to gateway")

        url = f'http://{server_address}:{server_port}/iot/send/{self.__device_id}'

        message = Messages.SendDataRequestMessage()
            
        i = 1
        for sensor in self.__sensors:
            new_sensor = message.sensors.add()
            new_sensor.id = i
            new_sensor.value = self.get_measure(sensor['min'], sensor['max'])
            i = i + 1

        i = 1
        for actuator in self.__actuators:
            new_actuator = message.actuators.add()
            new_actuator.id = i
            logging.debug(f"actuator state: {actuator['state']}")
            new_actuator.state = actuator['state']
            i = i + 1

        logging.debug(message)
        try:
            requests.post(url, data=message.SerializeToString(), headers=headers)
        except Exception:
            pass
    

    def send_sensor_data(self, server_address, server_port):
        logging.info("sending data to gateway")

        url = f'http://{server_address}:{server_port}/iot/send/{self.__device_id}'
        
        while True:
            message = Messages.SendDataRequestMessage()
            
            i = 1
            for sensor in self.__sensors:
                new_sensor = message.sensors.add()
                new_sensor.id = i
                new_sensor.value = self.get_measure(sensor['min'], sensor['max'])
                i = i + 1

            i = 1
            for actuator in self.__actuators:
                new_actuator = message.actuators.add()
                new_actuator.id = i
                logging.debug(f"actuator state: {actuator['state']}")
                new_actuator.state = actuator['state']
                i = i + 1

            logging.debug(message)
            try:
                requests.post(url, data=message.SerializeToString(), headers=headers)
            except Exception:
                continue
            time.sleep(self.__sensor_interval)


    def join_gateway(self, server_address, server_port):
        url = f'http://{server_address}:{server_port}/iot/join'

        logging.info(url)

        join_request_message = Messages.JoinRequestMessage()
        join_request_message.name = self.__device_name
        join_request_message.udp_ip = self.__udp_server_address
        join_request_message.udp_port = self.__udp_server_port

        
        #insert sensors data
        id = 1
        for sensor in self.__sensors:
            new_sensor = join_request_message.sensors.add()
            new_sensor.name = sensor['name']
            new_sensor.id = id
            id = id + 1

        #insert actuators
        id = 1
        for actuator in self.__actuators:
            new_actuator = join_request_message.actuators.add()
            new_actuator.name = actuator['name']
            new_actuator.id = id
            id = id + 1

        #TODO: maybe insert ip and port
        # join_request_message.ip
        # join_request_message.port

        response = requests.post(url,data=join_request_message.SerializeToString(),headers=headers)
        text_response = response.text

        response_object = Messages.JoinResponseMessage()
        response_object.ParseFromString(str.encode(text_response)) 
        
        logging.info(response_object)

        return response_object


        
    def wait_for_gateway_info(self):
        logging.info("setting up multicast socket")
        multicast_connection = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        multicast_connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

        multicast_connection.bind(('', self.__multicast_port))
        
        mreq = struct.pack("4sl", socket.inet_aton(self.__multicast_group), socket.INADDR_ANY)
        multicast_connection.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)
    
        logging.info("waiting for multicast message from gateway")
        [server_address, server_port] = multicast_connection.recv(BUFFER_SIZE).decode().split()

        logging.info(f"received gateway information with data: {server_address}:{server_port}")
        

        return (server_address, server_port)
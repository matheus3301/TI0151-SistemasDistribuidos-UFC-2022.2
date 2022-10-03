import socket
import protobuf.iot_pb2 as Messages
import requests

BUFFER_SIZE = 1024

class Device:

    def __init__(self, device_name: str ,multicast_group, multicast_port, sensors:dict, actuators:dict) -> None:
        self.__multicast_group = multicast_group
        self.__multicast_port = multicast_port
        self.__device_name = device_name
        self.__sensors = sensors
        self.__actuators = actuators

        (server_address, server_port) = self.wait_for_gateway_info()
        join_response = self.join_gateway(server_address, server_port)


    def join_gateway(self, server_address, server_port):
        url = f'http://{server_address}:{server_port}/iot/join'

        join_request_message = Messages.JoinRequestMessage()
        join_request_message.name = self.__device_name
        
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
            new_actuator = actuator['name']
            new_actuator.id = id + 1

        #TODO: maybe insert ip and port
        # join_request_message.ip
        # join_request_message.port

        response = requests.post(url,data=join_request_message.SerializeToString())
        text_response = response.text
        return Messages.JoinResponseMessage().ParseFromString(text_response)


        
    def wait_for_gateway_info(self):
        multicast_connection = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        multicast_connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

        multicast_connection.bind((self.__multicast_group, self.__multicast_port))
    
        [server_address, server_port] = multicast_connection.recv(BUFFER_SIZE).decode().split()

        return (server_address, server_port)
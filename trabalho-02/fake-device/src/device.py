import requests
import pika
import logging
import json
import time
import numpy as np
import random
import grpc
from concurrent import futures
import threading

from . import configuration, iot_pb2, iot_pb2_grpc

logging.basicConfig(level=logging.INFO,format='%(asctime)s %(levelname)s %(message)s')
class Device:
    def __init__(self, device_name : str, sensor_list : list, actuator_list : list, ttl: int, grpc_port: int) -> None:
        logging.info(f"starting the device called {device_name}")

        self.__device_name = device_name
        self.__sensor_list = sensor_list
        self.__actuator_list = actuator_list
        self.__ttl = ttl
        self.__grpc_port = grpc_port
        self.__alive = True

        for actuator in self.__actuator_list:
            actuator.update({'state': False})


        for sensor in self.__sensor_list:
            sensor['pdf'] = np.random.normal(sensor['mean'], sensor['sd'], configuration.PDF_SIZE)

        try:
            logging.info(f"trying to connect to gateway and create the device")
            self.__uuid = self.join_on_gateway()
        except Exception:
            logging.error("could not create device on the gateway, shutting down")
            exit(1)

        self.__amqp_channel = self.connect_on_rabbitmq()

        logging.info("starting to listen to remote commands")
        grpc_server = self.listen_to_remote_commands()

        logging.info("starting to send data to broker")
        self.generate_fake_data_and_send_info_periodically()
        
        logging.info("shutting down the device!")

        grpc_server.stop(grace=None)
        self.quit_on_gateway()

        logging.info("done!")

    def join_on_gateway(self):
        request_body = {
            'name': self.__device_name,
            'sensors': [],
            'actuators': [],
            'grpc_port': self.__grpc_port
        }

        i = 1
        for sensor in self.__sensor_list:
            request_body['sensors'].append({
                'id': i,
                'name': sensor['name']
            })

            i += 1

        i = 1
        for actuator in self.__actuator_list:
            request_body['actuators'].append({
                'id': i,
                'name': actuator['name']
            })

            i += 1
        
        logging.debug(request_body)

        response = requests.post(f"{configuration.GATEWAY_HOST}:{configuration.GATEWAY_PORT}/iot/devices", json=request_body)

        response_body = json.loads(response.content)
        logging.debug(response_body)

        logging.info(f"created this device sucessfully on the gateway, got the id {response_body['uuid']}")
        return response_body['uuid']    

    def connect_on_rabbitmq(self):
        connection = pika.BlockingConnection(pika.ConnectionParameters(host=configuration.RABBITMQ_HOST, credentials=pika.PlainCredentials('guest','guest')))
        channel = connection.channel()

        channel.queue_declare(queue=configuration.RABBITMQ_QUEUE_NAME, durable=True)
        
        return channel

    def generate_fake_data_and_send_info_periodically(self):
        while self.__alive:
            body = {'uuid': self.__uuid, 'sensors': [], 'actuators': []}

            i = 1
            for sensor in self.__sensor_list:
                body['sensors'].append({
                    'id': i,
                    'value': round(random.choice(sensor['pdf']),2)
                })

                i += 1

            i = 1
            for actuator in self.__actuator_list:
                body['actuators'].append({
                    'id': i,
                    'state': actuator['state']
                })

                i += 1

            self.send_sensor_data(body)

            time.sleep(self.__ttl)


    def send_sensor_data(self, body):
        logging.info("sending new status data")
        logging.debug(body)
        self.__amqp_channel.basic_publish(exchange='', routing_key=configuration.RABBITMQ_QUEUE_NAME, body=json.dumps(body))

    def set_alive_to_false(self):
        self.__alive = False
    
    def quit_on_gateway(self):
        return requests.delete(f"{configuration.GATEWAY_HOST}:{configuration.GATEWAY_PORT}/iot/devices/{self.__uuid}")

    def toggle_actuator(self, id):
        if len(self.__actuator_list) < id:
            return False
        else:
            self.__actuator_list[id - 1]['state'] = not self.__actuator_list[id - 1]['state']
            return True
    
    def listen_to_remote_commands(self):
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=5))
        iot_pb2_grpc.add_DeviceServiceServicer_to_server(
            RemoteService(
                quit_method=self.set_alive_to_false,
                toggle_method=self.toggle_actuator
                ),
            server            
            )
        
        server.add_insecure_port(f"[::]:{self.__grpc_port}")
        server.start()

        logging.info(f"started listening to remote grpc commands on the port {self.__grpc_port}")

        # server.wait_for_termination()

        return server

class RemoteService(iot_pb2_grpc.DeviceService):
    def __init__(self, quit_method, toggle_method):
        self.__quit_method = quit_method
        self.__toggle_method = toggle_method

    def ShutdownDevice(self, request, context):
        logging.info("received a command to shutdown the device")
        self.__quit_method()
        return iot_pb2.Empty()
        

    def ToggleActuator(self, request, context):
        logging.info(f"received a command to toggle the actuator {request.id}")
        ans = self.__toggle_method(request.id)
        return iot_pb2.ToggleActuatorResponse(ok=ans)

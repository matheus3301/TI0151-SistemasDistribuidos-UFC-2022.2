import requests
import pika
import logging
import json

from . import configuration

logging.basicConfig(level=logging.INFO,format='%(asctime)s %(levelname)s %(message)s')
class Device:
    def __init__(self, device_name : str, sensor_list : list, actuator_list : list) -> None:
        logging.info(f"starting the device called {device_name}")

        self.__device_name = device_name
        self.__sensor_list = sensor_list
        self.__actuator_list = actuator_list

        try:
            logging.info(f"trying to connect to gateway and create the device")
            self.__uuid = self.join_on_gateway()
        except Exception:
            logging.error("could not create device on the gateway, shutting down")
            exit(1)

        self.__amqp_channel = self.connect_on_rabbitmq()
        self.__amqp_channel.basic_publish(exchange='', routing_key=configuration.RABBITMQ_QUEUE_NAME, body=json.dumps({
            'uuid': self.__uuid,
            'sensors': [{'id': 1, 'value': 100}]
        }))

    def join_on_gateway(self):
        request_body = {
            'name': self.__device_name,
            'sensors': [],
            'actuators': []
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

    def send_sensor_data(self):
        pass
    
    def quit_on_gateway(self):
        pass

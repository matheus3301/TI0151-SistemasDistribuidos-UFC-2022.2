import pika

RABBITMQ_HOST="localhost"
RABBITMQ_PORT=5672
RABBITMQ_USER="guest"
RABBITMQ_PASSWORD="guest"
RABBITMQ_EXCHANGE_NAME="device.status"

GATEWAY_HOST="http://localhost"
GATEWAY_PORT=8080

PDF_SIZE = 100

message_props = pika.BasicProperties(
            content_type='application/json',
            content_encoding='utf-8',
        )
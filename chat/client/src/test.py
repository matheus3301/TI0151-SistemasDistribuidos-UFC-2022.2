from enum import Enum
import logging
import json
import socket
import threading

logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(levelname)s %(message)s')

HOST = "localhost"
PORT = 7777
BUFFER_SIZE = 6144


class MessageTypes(Enum):
    LOGIN = 1
    TEXT = 2
    USERS = 3
    LOGOUT = 4
    ERROR = 5


def main():

    connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    # connection.bind(('localhost', 8081))
    connection.connect((HOST, PORT))

    nickname = input("Digite seu nome: ")

    connection.sendall(json.dumps({
        'type': MessageTypes.LOGIN.name,
        'nickname': nickname
    }).encode())

    incoming_messages_thread = threading.Thread(
        target=handle_incoming_messages, args=(connection,))

    # setting the thread as daemon, to die with its parent
    incoming_messages_thread.start()

    while True:
        message = input()

        if message == '/sair':
            connection.sendall(
                json.dumps({
                    'type': MessageTypes.LOGOUT.name,
                    'nickname': nickname
                }).encode()

            )

        elif message == '/usuarios':
            connection.sendall(
                json.dumps({
                    'type': MessageTypes.USERS.name  
                }).encode()
            )    

        else:    
            connection.sendall(json.dumps({
                'type': MessageTypes.TEXT.name,
                'message': message
            }).encode())


def handle_incoming_messages(conneciton: socket):
    while True:
        logging.info("a")
        message = conneciton.recv(BUFFER_SIZE).decode()

        logging.info("c")
        sanitized_message = json.loads(message)

        logging.info("d")

        logging.debug(f"recovered message from server")
        logging.debug(sanitized_message)


if __name__ == "__main__":
    main()

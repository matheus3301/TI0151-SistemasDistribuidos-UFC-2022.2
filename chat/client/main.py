from enum import Enum
import logging
import json
import socket
import threading
from pyfiglet import Figlet
import os

from src.message_fields import MessageFields
from src.message_types import MessageTypes

logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s %(levelname)s %(message)s')

HOST = "localhost"
PORT = 7777
BUFFER_SIZE = 2048

def main():
    while True:
        try:
            connection = False
            clear_console()
            figlet = Figlet(font='slant')
            print(figlet.renderText('PyCHAT'))
            login = ""
            while(login != "/entrar"):
                login = input("Digite /entrar: \n")
            
            HOST = input("informe o IP: ")
            PORT = int(input("informe a Porta: "))

            connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            # connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            # connection.bind(('localhost', 8081))
            connection.connect((HOST, PORT))
            incoming_messages_thread = threading.Thread(
            target=handle_incoming_messages, args=(connection,))
            # incoming_messages_thread.setDaemon(True)
            # setting the thread as daemon, to die with its parent
            incoming_messages_thread.start()

            logging.debug('connection with server has been done successfully')
            nickname = input("digite seu nickname: ")

            send_message_to_server(connection, MessageTypes.LOGIN, nickname)

            while True:
                input_message = input()
                input_message = input_message.strip()

                if input_message == '/sair':
                    send_message_to_server(connection, MessageTypes.LOGOUT, '')
                    break
                elif input_message == '/usuarios':
                    send_message_to_server(connection, MessageTypes.USERS, '')
                else:
                    send_message_to_server(connection, MessageTypes.TEXT, input_message)
        except ValueError:
            logging.error(f"value not supported")
        except socket.error:
            logging.error(f"connection lost with the server")
        except KeyboardInterrupt:
            if connection != False:
                closing_connection(connection)
            exit()


def send_message_to_server(connection: socket, message_type: MessageTypes, message: str):
    connection.sendall(json.dumps({MessageFields.TYPE: message_type.name,MessageFields.MESSAGE: message}).encode())

def handle_incoming_messages(connection: socket):
    while True:
        message = connection.recv(BUFFER_SIZE).decode()

        sanitized_message = json.loads(message)
        logging.debug(f"recovered message from server")
        logging.debug(sanitized_message)

        if sanitized_message[MessageFields.TYPE] == MessageTypes.TEXT.name:
            print(sanitized_message[MessageFields.MESSAGE])
        elif sanitized_message[MessageFields.TYPE] == MessageTypes.ERROR.name:
            print('\033[91m'+sanitized_message[MessageFields.MESSAGE])
        elif sanitized_message[MessageFields.TYPE] == MessageTypes.USERS.name:
            print("Lista de Usuários:")
            for nickname in sanitized_message[MessageFields.MESSAGE]:
                print(nickname)
        elif sanitized_message[MessageFields.TYPE] == MessageTypes.LOGOUT.name:
            closing_connection(connection)

def clear_console():
    command = 'clear'
    if os.name in ('nt', 'dos'):  # If Machine is running on Windows, use cls
        command = 'cls'
    os.system(command)

def closing_connection(connection: socket):
    print("Encerrando a aplicação")
    send_message_to_server(connection, MessageTypes.LOGOUT, '')
    connection.close()
    clear_console()

if __name__ == "__main__":
    main()
from enum import Enum
import logging
import json
import socket
import threading

from .message_fields import MessageFields
from .message_types import MessageTypes

logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(levelname)s %(message)s')

# TODO: propagar entrada e saída de usuários


class Server:
    def __init__(self, config: dict) -> None:
        self.__config = config
        self.__clients = {}

        logging.info("starting chat server")

        # tcp config
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.connection.bind((self.__config["HOST"], self.__config["PORT"]))
        self.connection.listen(self.__config["MAX_CLIENTS"])

        logging.info(
            f"server is avaliable on {self.__config['HOST']}:{self.__config['PORT']}")

    def start(self) -> None:
        while True:
            try:
                (client_socket, client_address) = self.connection.accept()

                # for each new connection, a new thread to handle incoming messages from a client
                thread = threading.Thread(
                    target=self.handle_client_connection, args=(client_socket, client_address))

                # setting the thread as daemon, to die with its parent
                thread.setDaemon(True)

                thread.start()

            except KeyboardInterrupt:
                logging.info(f"server is shutting down")
                self.connection.close()
                exit()

    def handle_client_connection(self, client_socket: socket, client_address) -> None:
        logging.info(f"new connection from {client_address}")

        while True:
            sanitized_message = {}
            message = ''

            try:
                message = client_socket.recv(
                    self.__config['BUFFER_SIZE']).decode()

                if not message:
                    continue

                sanitized_message = json.loads(message)
                logging.debug(f"recovered message from {client_address}")
                logging.debug(sanitized_message)

                logging.info(
                    f"{sanitized_message[MessageFields.TYPE]} message from {client_address}")

                # handling user login
                if sanitized_message[MessageFields.TYPE] == MessageTypes.LOGIN.name:
                    if not self.__clients.get(client_address, False):
                        logging.info(
                            f"{sanitized_message[MessageFields.MESSAGE]} is entering on the chat")
                        self.__clients[client_address] = (
                            client_socket, sanitized_message[MessageFields.MESSAGE])

                        for other_client_address in self.__clients:
                            (other_client_socket,
                                    other_client_nickname) = self.__clients[other_client_address]   
                            other_client_socket.sendall(
                                json.dumps(
                                    {
                                        MessageFields.TYPE: MessageTypes.TEXT.name,
                                        MessageFields.MESSAGE: f"SERVIDOR: {sanitized_message[MessageFields.MESSAGE]} entrou no chat!"
                                    }
                                ).encode()
                            )
                    else:
                        logging.error(
                            f"{client_address} is already logged in the chat")

                        client_socket.sendall(
                            json.dumps(
                                {
                                    MessageFields.TYPE: MessageTypes.ERROR.name,
                                    MessageFields.MESSAGE: f"SERVIDOR: {sanitized_message[MessageFields.MESSAGE]} já está no chat!"
                                }
                            ).encode()
                        )

                # handling user logout
                elif sanitized_message[MessageFields.TYPE] == MessageTypes.LOGOUT.name:
                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not logged on the chat")

                        client_socket.sendall(
                            json.dumps(
                                {
                                    MessageFields.TYPE: MessageTypes.ERROR.name,
                                    MessageFields.MESSAGE: f"SERVIDOR: você não está no chat!"
                                }
                            ).encode()
                        )
                    else:
                        (saved_socket,
                         nickname) = self.__clients[client_address]
                        logging.info(f"removing {nickname} from the chat")
                        for other_client_address in self.__clients:
                            (other_client_socket,
                                    other_client_nickname) = self.__clients[other_client_address]   
                            other_client_socket.sendall(
                                json.dumps(
                                    {
                                        MessageFields.TYPE: MessageTypes.TEXT.name,
                                        MessageFields.MESSAGE: f"SERVIDOR: {nickname} saiu do chat!"
                                    }
                                ).encode()
                            )
                        self.__clients.pop(client_address)

                # handling user text messages
                elif sanitized_message[MessageFields.TYPE] == MessageTypes.TEXT.name:
                    (saved_socket, nickname) = self.__clients[client_address]

                    text_message = sanitized_message[MessageFields.MESSAGE]

                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not able to send message because its not logged")

                        self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Você não está autorizado, entre no chat!')
                    else:
                        self.send_message_to_group_as_client(client_address, MessageTypes.TEXT, text_message)

                elif sanitized_message[MessageFields.TYPE] == MessageTypes.USERS.name:
                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not able to send message because its not logged")

                        self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Você não está autorizado, entre no chat!')
                    else:
                        # sending list of all users to the client
                        nickname_list = [self.__clients[client][1] for client in self.__clients]                     
                        self.send_message_to_client_as_server(client_socket, MessageTypes.USERS, nickname_list)
                else:
                    self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Tipo inválido de mensagem')
            except socket.error:
                logging.error(
                    f"connection with {client_address} has been lost")

                if self.__clients.get(client_address, False) != False:
                    self.__clients.pop(client_address)

                return None
            except json.JSONDecodeError:
                logging.error(
                    f"unable to decode message from {client_address}")

                self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Impossível decodificar messagem recebida')
            except TypeError:
                logging.error(f"type error on message from {client_address}")

                self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Erro de tipo na mensagem')
            except KeyError:
                logging.error(f"invalid message from {client_address}")

                self.send_message_to_client_as_server(client_socket, MessageTypes.ERROR, 'SERVIDOR: Mensagem inválida, campos requeridos não preenchidos')

    def send_message_to_group_as_client(self, client_address, message_type: MessageTypes, message: str, itself:bool = False) -> None:
        (client_socket, nickname) = self.__clients[client_address]
        
        final_sent_message = f"{nickname}: {message}"

        for other_client_address in self.__clients:
            if itself or other_client_address != client_address:
                (other_client_socket,
                    other_client_nickname) = self.__clients[other_client_address]


                other_client_socket.sendall(json.dumps({
                    MessageFields.TYPE: message_type.name,
                    MessageFields.MESSAGE: final_sent_message
                }).encode())

    def send_message_to_client_as_server(self, client_socket: socket,  message_type: MessageTypes, message) -> None:
        client_socket.sendall(
            json.dumps(
                {
                    MessageFields.TYPE: message_type.name,
                    MessageFields.MESSAGE: message
                }
            ).encode()
        )




from enum import Enum
import logging
import json
import socket
import threading


logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(levelname)s %(message)s')

# TODO: substituir keys do dict por enum
# TODO: encapsular envio de mensagens (global e para o próprio usuário)
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
                    f"{sanitized_message['type']} message from {client_address}")

                # handling user login
                if (sanitized_message['type'] == MessageTypes.LOGIN.name):
                    if not self.__clients.get(client_address, False):
                        logging.info(
                            f"{sanitized_message['nickname']} is entering on the chat")
                        self.__clients[client_address] = (
                            client_socket, sanitized_message['nickname'])

                        for other_client_address in self.__clients:
                            (other_client_socket,
                                    other_client_nickname) = self.__clients[other_client_address]   
                            other_client_socket.sendall(
                                json.dumps(
                                    {
                                        'type': MessageTypes.TEXT.name,
                                        'message': f"SERVIDOR: {sanitized_message['nickname']} entrou no chat!"
                                    }
                                ).encode()
                            )
                    else:
                        logging.error(
                            f"{client_address} is already logged in the chat")

                        client_socket.sendall(
                            json.dumps(
                                {
                                    'type': MessageTypes.ERROR.name,
                                    'message': f"SERVIDOR: {sanitized_message['nickname']} já está no chat!"
                                }
                            ).encode()
                        )

                # handling user logout
                if (sanitized_message['type'] == MessageTypes.LOGOUT.name):
                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not logged on the chat")

                        client_socket.sendall(
                            json.dumps(
                                {
                                    'type': MessageTypes.ERROR.name,
                                    'message': f"SERVIDOR: {sanitized_message['nickname']} não está no chat!"
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
                                        'type': MessageTypes.TEXT.name,
                                        'message': f"SERVIDOR: {sanitized_message['nickname']} saiu do chat!"
                                    }
                                ).encode()
                            )
                        self.__clients.pop(client_address)

                # handling user text messages
                if (sanitized_message['type'] == MessageTypes.TEXT.name):
                    (saved_socket, nickname) = self.__clients[client_address]

                    text_message = sanitized_message['message']

                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not able to send message because its not logged")
                    else:
                        # send message to all clients (except itself)
                        for other_client_address in self.__clients:
                            if other_client_address != client_address:
                                (other_client_socket,
                                    other_client_nickname) = self.__clients[other_client_address]

                                final_sent_message = f"{nickname}: {text_message}"

                                other_client_socket.sendall(json.dumps({
                                    'type': MessageTypes.TEXT.name,
                                    'message': final_sent_message
                                }).encode())

                if (sanitized_message['type'] == MessageTypes.USERS.name):
                    if not self.__clients.get(client_address, False):
                        logging.error(
                            f"{client_address} is not able to send message because its not logged")
                    else:
                        # sending list of all users to the client
                        list_client_message = f"Lista de Usuários:\n"
                        for other_client_address in self.__clients:
                            (other_client_socket,
                                other_client_nickname) = self.__clients[other_client_address]

                            list_client_message = list_client_message + f"Nickname: {other_client_nickname}\n"

                        client_socket.sendall(json.dumps({
                            'type': MessageTypes.TEXT.name,
                            'message': list_client_message
                        }).encode())                                

            except socket.error:
                logging.error(
                    f"connection with {client_address} has been lost")

                if self.__clients.get(client_address, False) != False:
                    self.__clients.pop(client_address)

                return None
            except json.JSONDecodeError:
                logging.error(
                    f"unable to decode message from {client_address}")

                client_socket.sendall(
                    json.dumps(
                        {
                            'type': MessageTypes.ERROR.name,
                            'message': 'Impossível decodificar messagem recebida'
                        }
                    ).encode()
                )
            except TypeError:
                logging.error(f"type error on message from {client_address}")

                client_socket.sendall(
                    json.dumps(
                        {
                            'type': MessageTypes.ERROR.name,
                            'message': 'Erro de tipo na mensagem'
                        }
                    ).encode()
                )
            except KeyError:
                logging.error(f"invalid message from {client_address}")

                client_socket.sendall(
                    json.dumps(
                        {
                            'type': MessageTypes.ERROR.name,
                            'message': 'Mensagem inválida, campos requeridos não preenchidos'
                        }
                    ).encode()
                )


class MessageTypes(Enum):
    LOGIN = 1
    TEXT = 2
    USERS = 3
    LOGOUT = 4
    ERROR = 5

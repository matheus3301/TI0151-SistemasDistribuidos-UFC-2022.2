import logging, json, socket

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

class Server:
    def __init__(self, config:dict) -> None:
        self.__config = config
        self.__clients = []
        
        logging.info("starting chat server")

        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connection.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.connection.bind((self.__config["HOST"], self.__config["PORT"]))
        self.connection.listen(self.__config["MAX_CLIENTS"])

        logging.info(f"server is avaliable on {self.__config['HOST']}:{self.__config['PORT']}")

        while True:
            try:
                (client_socket, client_address) = self.connection.accept()
                
            except KeyboardInterrupt:
                logging.info(f"server is shutting down")
                self.connection.close()
                exit()
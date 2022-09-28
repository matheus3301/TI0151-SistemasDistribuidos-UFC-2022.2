import logging, json, socket

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

class Server:
    def __init__(self, config:dict) -> None:

        logging.info("starting chat server")
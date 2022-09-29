from src.server import Server


def main():
    server = Server({
        'HOST': 'localhost',
        'PORT': 7777,
        'MAX_CLIENTS': 50,
        'BUFFER_SIZE': 2048
    })

    server.start()


if __name__ == "__main__":
    main()

from enum import Enum


class MessageTypes(Enum):
    LOGIN = 1
    TEXT = 2
    USERS = 3
    LOGOUT = 4
    ERROR = 5

from enum import Enum


class MessageTypes(Enum):
    LOGIN = 1
    TEXT = 2
    LOGOUT = 3
    ERROR = 4

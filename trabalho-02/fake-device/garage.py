from src.device import Device

device = Device(
    "Garage",
    [
        {'name': 'Sensor de Luminosidade', 'mean': 50, 'sd': 4},
        {'name': 'Sensor de Fumaça', 'mean': 10, 'sd': 2},
    ],
    [
        {'name': 'Portão'},
    ],
    3,
    7773
    )
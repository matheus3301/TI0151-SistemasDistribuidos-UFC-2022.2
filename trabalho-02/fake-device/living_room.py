from src.device import Device

device = Device(
    "Living Room",
    [
        {'name': 'Sensor de Luminosidade', 'min': 40, 'max': 60},
        {'name': 'Sensor de Temperatura', 'min': 10, 'max': 30},
    ],
    [
        {'name': 'Ar Condicionado'},
        {'name': 'Televisão'}
    ]
    )
from src.device import Device

device = Device(
    "Sala de Estar",
    [
        {'name': 'Sensor de Luminosidade', 'mean': 50, 'sd': 4},
        {'name': 'Sensor de Temperatura', 'mean': 10, 'sd': 2},
    ],
    [
        {'name': 'Ar Condicionado'},
        {'name': 'Televisão'}
    ],
    3,
    7771
    )
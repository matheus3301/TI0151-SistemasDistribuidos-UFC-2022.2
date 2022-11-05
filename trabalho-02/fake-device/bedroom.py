from src.device import Device

device = Device(
    "Quarto",
    [
        {'name': 'Sensor de Luminosidade', 'mean': 50, 'sd': 4},
        {'name': 'Sensor de Temperatura', 'mean': 10, 'sd': 2},
    ],
    [
        {'name': 'Luz'},
        {'name': 'Cortina'}
    ],
    3,
    7772
    )
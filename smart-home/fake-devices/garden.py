from src.device import Device

garden = Device(
    'Quintal', 
    '230.0.0.0', 
    7777,
    'localhost',
    1113, 
    [
        {'name': 'Sensor de Umidade', 'min': 20, 'max': 33.3}
    ], 
    [
        {'name': 'Irrigador'}
    ],
    5
    )


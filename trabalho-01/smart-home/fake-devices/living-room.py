from src.device import Device

living_room = Device(
    'Sala de Estar', 
    '230.0.0.0', 
    7777,
    'localhost',
    1111, 
    [
        {'name':'Luminosidade', 'min': 50, 'max': 70}, 
        {'name':'Temperatura', 'min': 30, 'max': 34}
    ], 
    [
        {'name':'Cortina'}
    ],
    5
    )


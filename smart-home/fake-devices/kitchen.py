from src.device import Device

kitchen = Device(
    'Cozinha', 
    '230.0.0.0', 
    7777,
    'localhost',
    1112, 
    [
        {'name':'Sensor de Fumaça', 'min': 0, 'max': 20}, 
        {'name':'Sensor de Gás Butano', 'min': 0, 'max': 35},
    ], 
    [
        {'name':'Coifa'},
        {'name':'Geladeira'},
        {'name':'Torneira da Pia'},
    ],
    5
    )


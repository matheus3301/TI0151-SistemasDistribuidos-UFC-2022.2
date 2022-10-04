import src.protobuf.iot_pb2 as Messages
import src.protobuf.api_pb2 as ApiMessages

response = Messages.JoinResponseMessage()
response.id = "1231"

# print(response)
# print(response.SerializeToString())

request = Messages.JoinRequestMessage()
request.name = 'Quarto'
# request.sensors = [Messages.JoinRequestMessage.Sensor(name="Temperatura"), Messages.JoinRequestMessage.Sensor(name="Luminosidade")]
# request.actuators = [Messages.JoinRequestMessage.Actuator(name="Luz"), Messages.JoinRequestMessage.Actuator(name="Cortina")]
temperature = request.sensors.add()

temperature.id = 1
temperature.name = "Temperatura"
# print(request)

f = open("fake-devices/devices.txt", "rb")

# print(f.read())

device_list =  ApiMessages.DeviceList()
device_list.ParseFromString(f.read())
print(device_list)

f.close()


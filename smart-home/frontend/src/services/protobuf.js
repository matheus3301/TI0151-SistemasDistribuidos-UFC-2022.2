import protobuf from 'protobufjs';
import proto from './api.proto';
import api from './api';
import Notification from '../utils/notification';

export const getDevices = async (setDevicesList) => {
    try {
        const response = await fetch(process.env.REACT_APP_API_URL + '/devices')
        const apiMessage = await response.arrayBuffer()
        const apiMessageBuffer = new Uint8Array(apiMessage)
        const root = await protobuf.load(proto);
        const protoDeviceList = root.lookupType('apipackage.ListAllDevicesInformationAndHistoryResponse');
        const message = protoDeviceList.decode(apiMessageBuffer)
        var devicesList = protoDeviceList.toObject(message).devices
        if(devicesList)
            setDevicesList(devicesList);
    } catch (e) {
        console.log(e)
    }
};

export const toggleActuator = async (device_id, actuator_id, setDevicesList) => {
    try {
        api.post(`/devices/${device_id}/actuators/${actuator_id}/toggle`)
        getDevices(setDevicesList);
    } catch (e) {
        console.log(e);
    }
}

export const scanDevices = async(setDevicesList) => {
    try {
        api.get('/management/scan');
        Notification('success', 'Procurando por dispositivos na rede...')
        getDevices(setDevicesList);
    } catch (e) {
        console.log(e);
    }
}
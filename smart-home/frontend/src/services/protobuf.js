import protobuf from 'protobufjs';
import proto from './api.proto';

export const getDevices = async (setDevicesList) => {
    try {
        const response = await fetch(process.env.REACT_APP_API_URL + '/devices')
        const apiMessage = await response.arrayBuffer()
        const apiMessageBuffer = new Uint8Array(apiMessage)
        const root = await protobuf.load(proto);
        const protoDeviceList = root.lookupType('apipackage.DeviceList');
        const message = protoDeviceList.decode(apiMessageBuffer)
        console.log(protoDeviceList.toObject(message))
        if(protoDeviceList.toObject(message).devices)
            setDevicesList(protoDeviceList.toObject(message).devices);
    } catch (e) {
        console.log(e)
    }
};

export const getDevice = async(setDevice, device_id) => {
    try {
        const response = await fetch(process.env.REACT_APP_API_URL + `/device/${device_id}`)
        const apiMessage = await response.arrayBuffer()
        const apiMessageBuffer = new Uint8Array(apiMessage)
        const root = await protobuf.load(proto);
        const protoDevice = root.lookupType('apipackage.Device');
        const message = protoDevice.decode(apiMessageBuffer)
        if(protoDevice.toObject(message))
            setDevice(protoDevice.toObject(message));
    } catch (e) {
        console.log(e)
    }
};
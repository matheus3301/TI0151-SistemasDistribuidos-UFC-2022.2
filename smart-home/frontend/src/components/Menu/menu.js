import protobuf from 'protobufjs';
import { useEffect, useState } from 'react';
import proto from '../../services/api.proto'

const Menu = () => {

    const [devicesList, setDevicesList] = useState([]);

    const getDevices = async () => {
        try {
            const response = await fetch(process.env.REACT_APP_API_URL + '/devices')
            const apiMessage = await response.arrayBuffer()
            const apiMessageBuffer = new Uint8Array(apiMessage)
            const root = await protobuf.load(proto);
            const protoDeviceList = root.lookupType('apipackage.DeviceList');
            const message = protoDeviceList.decode(apiMessageBuffer)
            setDevicesList(protoDeviceList.toObject(message).devices);
        } catch (e) {
            console.log(e)
        }
    };

    useEffect(() => {
        getDevices();
    }, [])


    return (
        <>
        {devicesList.map( (device) => <h1 key={device.id}>{device.name}</h1>)}
        </>
    );
};

export default Menu;
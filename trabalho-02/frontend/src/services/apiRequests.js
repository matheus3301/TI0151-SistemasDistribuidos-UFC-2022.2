import api from './api';
import Notification from '../utils/notification';

export const getDevices = async (setDevicesList) => {
    try {
        const response = await api.get(`/devices`);
        if (response.status === 200){
            setDevicesList(response.data)
        }
    } catch (e){
        console.log(e);
    }
};

export const toggleActuator = async (device_id, actuator_id, setDevicesList) => {
    try {
        const response = await api.post(`/devices/${device_id}/actuators/${actuator_id}/toggle`);
        if (response.status === 200){
            Notification('success', 'Atuador modificado com sucesso!')
            getDevices(setDevicesList);
        }
    } catch (e) {
        console.log(e);
    }
}

export const shutdownDevice = async(device_id, setDevicesList) => {
    try {
        const response = await api.post(`/devices/${device_id}/actions/shutdown`)
        if (response.status === 200){
            Notification('success', 'Dispositivo desligado com sucesso!');
            getDevices(setDevicesList);
        }
    } catch (e){
        console.log(e)
    }
} 
package br.ufc.smarthome.repositories;


import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.models.Models;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class DeviceRepository {
    private final Map<String, DeviceEntity> devices;

    @Getter
    private final int limit;

    public DeviceEntity getById(String uuid){
        return this.devices.get(uuid);
    }

    public Collection<DeviceEntity> getAll(){
        return this.devices.values();
    }

    public DeviceEntity add(DeviceEntity device){
        this.devices.put(device.getUuid(), device);
        return this.getById(device.getUuid());
    }
}

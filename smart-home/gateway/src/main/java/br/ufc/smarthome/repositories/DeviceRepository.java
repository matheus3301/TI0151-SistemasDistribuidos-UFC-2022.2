package br.ufc.smarthome.repositories;


import br.ufc.smarthome.entities.DeviceEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
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
        log.info(device.toString());

        while(this.getById(device.getUuid()) != null){
            log.info("trying a new identifier to new device");
            device.setUuid(UUID.randomUUID().toString());
        }

        this.devices.put(device.getUuid(), device);
        return this.getById(device.getUuid());
    }

    public boolean deleteById(String uuid){
        var removedDevice = this.devices.remove(uuid);

        return removedDevice != null;
    }
}

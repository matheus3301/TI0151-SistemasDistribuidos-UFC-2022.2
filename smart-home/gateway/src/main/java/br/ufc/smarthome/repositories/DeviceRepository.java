package br.ufc.smarthome.repositories;


import br.ufc.smarthome.models.Models;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class DeviceRepository {
    private final Map<String, Models.Device> devices;

    public Models.Device getById(String id){
        return this.devices.get(id);
    }

    public Collection<Models.Device> getAll(){
        return this.devices.values();
    }
}

package br.ufc.smarthome.services;

import br.ufc.smarthome.models.Device;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public Device createDevice(String name){
        Device device = Device
                .builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .build();

        return deviceRepository.save(device);
    }

    public Collection<Device> listAll(){
        return (Collection<Device>) deviceRepository.findAll();
    }
}

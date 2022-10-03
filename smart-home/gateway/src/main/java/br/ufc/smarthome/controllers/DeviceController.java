package br.ufc.smarthome.controllers;

import br.ufc.smarthome.models.Models;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceRepository deviceRepository;

    @GetMapping("/{id}")
    public Models.Device getDevice(@PathVariable String id){
        return deviceRepository.getById(id);
    }

    @GetMapping
    public Models.DeviceList getDeviceList(){
        return Models.DeviceList.newBuilder().addAllDevices(deviceRepository.getAll()).build();
    }
}


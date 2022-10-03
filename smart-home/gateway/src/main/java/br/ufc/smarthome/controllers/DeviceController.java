package br.ufc.smarthome.controllers;

import br.ufc.smarthome.models.Models;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {
    private final DeviceRepository deviceRepository;

    @GetMapping("/{id}")
    public Models.Device getDevice(@PathVariable String id){
//        return deviceRepository.getById(id);
        return null;
    }

    @GetMapping
    public Models.DeviceList getDeviceList(){
        log.info("size: {}", deviceRepository.getAll().size());
//        return Models.DeviceList.newBuilder().addAllDevices(deviceRepository.getAll()).build();
        return null;
    }
}


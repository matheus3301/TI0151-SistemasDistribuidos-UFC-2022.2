package br.gov.ufc.homeassistantserver.controllers;

import br.gov.ufc.homeassistantserver.exceptions.DeviceNotFoundException;
import br.gov.ufc.homeassistantserver.models.Device;
import br.gov.ufc.homeassistantserver.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/iot/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceRepository deviceRepository;

    @GetMapping
    public ResponseEntity<Collection<Device>> listDevices(){
        return ResponseEntity.ok(deviceRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device){
        log.info("creating a new device on the system");

        device.setUuid(UUID.randomUUID().toString());
        for(var actuator : device.getActuators()){
            actuator.setHistory(new ArrayList<>());
        }

        for(var sensor : device.getSensors()){
            sensor.setHistory(new ArrayList<>());
        }

        return ResponseEntity.created(null).body(deviceRepository.add(device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id){
        if(deviceRepository.deleteById(id)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }else{
            throw new DeviceNotFoundException();
        }
    }
}
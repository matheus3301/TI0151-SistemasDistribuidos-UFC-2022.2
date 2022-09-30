package br.ufc.smarthome.controllers;

import br.ufc.smarthome.dtos.DeviceDTO;
import br.ufc.smarthome.models.Device;
import br.ufc.smarthome.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<Collection<Device>> index(){
        return ResponseEntity.ok(
                deviceService.listAll()
        );
    }

    @PostMapping
    public ResponseEntity<Device> create(@RequestBody DeviceDTO deviceDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                deviceService.createDevice(deviceDTO.getName())
        );
    }

}

package br.gov.ufc.homeassistantserver.controllers;

import br.gov.ufc.homeassistantserver.exceptions.DeviceNotFoundException;
import br.gov.ufc.homeassistantserver.models.Device;
import br.gov.ufc.homeassistantserver.repositories.DeviceRepository;
import br.gov.ufc.homeassistantserver.services.DeviceServiceGrpc;
import br.gov.ufc.homeassistantserver.services.Iot;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<Device> createDevice(@RequestBody Device device, HttpServletRequest request){
        log.info("creating a new device on the system");

        device.setUuid(UUID.randomUUID().toString());
        for(var actuator : device.getActuators()){
            actuator.setHistory(new ArrayList<>());
        }

        for(var sensor : device.getSensors()){
            sensor.setHistory(new ArrayList<>());
        }

        String remoteAddr = request.getRemoteAddr();
        if(remoteAddr.equals("0:0:0:0:0:0:0:1")){
            remoteAddr = "127.0.0.1";
        }

        device.setRemote_address(remoteAddr);

        return ResponseEntity.created(null).body(deviceRepository.add(device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id){
        log.info("deleting the device {}", id);

        if(deviceRepository.deleteById(id)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }else{
            throw new DeviceNotFoundException();
        }
    }

    @PostMapping("{deviceId}/actuators/{actuatorId}/toggle")
    public ResponseEntity<Void> toggleActuator(@PathVariable int actuatorId, @PathVariable String deviceId) throws InterruptedException {
        log.info("trying to toggle the actuator {} of {}", actuatorId, deviceId);
        Device device = deviceRepository.getById(deviceId);

        if(device != null){
            String target = String.format("%s:%d", device.getRemote_address(), device.getGrpc_port());
            log.info("Requesting to {}", target);

            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            boolean ans = false;

            try {
                var blockingStub = DeviceServiceGrpc.newBlockingStub(channel);
                Iot.ToggleActuatorRequest request = Iot.ToggleActuatorRequest.newBuilder().setId(actuatorId).build();
                var response = blockingStub.toggleActuator(request);
                ans = response.getOk();
            }catch (StatusRuntimeException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }finally {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

            }
            if(ans){
                return ResponseEntity.ok().body(null);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        throw new DeviceNotFoundException();
    }

    @PostMapping("/{id}/actions/shutdown")
    public ResponseEntity<Void> shutdownDevice(@PathVariable String id) throws InterruptedException {
        log.info("trying to shut down the device {}", id);
        Device device = deviceRepository.getById(id);
        if(device != null){
            String target = String.format("%s:%d", device.getRemote_address(), device.getGrpc_port());
            log.info("Requesting to {}", target);

            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

            try {
                var blockingStub = DeviceServiceGrpc.newBlockingStub(channel);
                Iot.Empty body = Iot.Empty.newBuilder().build();
                var response = blockingStub.shutdownDevice(body);

            }catch (StatusRuntimeException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }finally {
                channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            }

            return ResponseEntity.ok().body(null);
        }
        throw new DeviceNotFoundException();
    }
}

package br.ufc.smarthome.controllers;

import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.models.Api;
import br.ufc.smarthome.repositories.DeviceRepository;
import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {
    private final DeviceRepository deviceRepository;

    @DeleteMapping("/{deviceId}")
    void removeDeviceFromSystem(@PathVariable String deviceId){
        log.info("removing {} from system", deviceId);

        DeviceEntity device = deviceRepository.getById(deviceId);
        if(device != null){
            if(device.shutdown()){
                log.info("{} is shutting down",deviceId);
                if(deviceRepository.deleteById(deviceId)){
                    log.info("{} removed successfully", deviceId);
                }else{
                    log.error("could not find {} to delete", deviceId);
                }
            }else{
                log.error("could not contact {}", deviceId);
            }
        }else{
            log.error("could not find {} device", deviceId);
        }

    }

    @PostMapping("/{deviceId}/actuators/{actuatorId}/toggle")
    void toggleDeviceActuator(@PathVariable int actuatorId, @PathVariable String deviceId){
        log.info("toggling actuator {} of device {}", actuatorId, deviceId);

        DeviceEntity device = deviceRepository.getById(deviceId);
        if (device != null) {
            if(device.remoteToggle(actuatorId)){
                log.info("toggling went successfully");
            }else{
                log.info("could not toggle actuator");
            }
        }else{
            log.error("could not find {} device", deviceId);
        }

    }

    @GetMapping
    Api.ListAllDevicesInformationAndHistoryResponse getDashboardData(){
        return Api.ListAllDevicesInformationAndHistoryResponse
                .newBuilder()
                .addAllDevices(
                        deviceRepository
                                .getAll()
                                .stream()
                                .map(deviceEntity ->
                                        Api.DeviceInformationAndHistoryResponse
                                                .newBuilder()
                                                .setUuid(deviceEntity.getUuid())
                                                .setName(deviceEntity.getName())
                                                .addAllActuators(
                                                        deviceEntity.getActuators().stream().map(actuatorEntity ->
                                                                Api.DeviceInformationAndHistoryResponse.ActuatorInformationAndHistory
                                                                        .newBuilder()
                                                                        .setId(actuatorEntity.getId())
                                                                        .setName(actuatorEntity.getName())
                                                                        .addAllHistory(
                                                                                actuatorEntity
                                                                                        .getHistory()
                                                                                        .stream()
                                                                                        .map(status -> Api
                                                                                                .DeviceInformationAndHistoryResponse
                                                                                                .ActuatorInformationAndHistory
                                                                                                .Status
                                                                                                .newBuilder()
                                                                                                .setTimestamp(status.getTimestamp().toString())
                                                                                                .setValue(status.isValue())
                                                                                                .build())
                                                                                        .collect(Collectors.toList())
                                                                        )
                                                                        .build()
                                                        ).collect(Collectors.toList())
                                                )
                                                .addAllSensors(
                                                        deviceEntity.getSensors().stream().map(sensorEntity ->
                                                            Api.DeviceInformationAndHistoryResponse.SensorInformationAndHistory
                                                                    .newBuilder()
                                                                    .setId(sensorEntity.getId())
                                                                    .setName(sensorEntity.getName())
                                                                    .addAllHistory(
                                                                            sensorEntity
                                                                                    .getHistory()
                                                                                    .stream().map(measure -> Api
                                                                                            .DeviceInformationAndHistoryResponse
                                                                                            .SensorInformationAndHistory
                                                                                            .Measure
                                                                                            .newBuilder()
                                                                                            .setTimestamp(measure.getTimestamp().toString())
                                                                                            .setValue(measure.getValue())
                                                                                            .build()

                                                                                    )
                                                                                    .collect(Collectors.toList())
                                                                    )
                                                                    .build()
                                                        ).collect(Collectors.toList())
                                                )
                                                .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }
}


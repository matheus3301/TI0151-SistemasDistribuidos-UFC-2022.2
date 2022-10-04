package br.ufc.smarthome.controllers;

import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.models.Iot;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/iot")
@RequiredArgsConstructor
@Slf4j
public class IotController {
    private final DeviceRepository deviceRepository;

    @PostMapping(value = "/send/{id}")
    void deviceSendData(@PathVariable String id, @RequestBody Iot.SendDataRequestMessage message, HttpServletRequest request){
        log.info("receiving data from device {}",id);

        DeviceEntity device = deviceRepository.getById(id);

        if(device == null){
            log.error("could not find device on database");
            return;
        }

        for(Iot.SendDataRequestMessage.Sensor sensor : message.getSensorsList()){
            for(DeviceEntity.SensorEntity sensorEntity : device.getSensors()){
                if(sensorEntity.getId() == sensor.getId()){
                    log.info("found sensor on memory");
                    if(sensorEntity.getHistory().size() >= deviceRepository.getLimit()){
                        sensorEntity.getHistory().remove(0);
                    }
                    sensorEntity.getHistory().add(DeviceEntity.SensorEntity.Measure.builder().timestamp(LocalDateTime.now()).value(sensor.getValue()).build());
                }
            }
        }

        for(Iot.SendDataRequestMessage.Actuator actuator : message.getActuatorsList()){
            for(DeviceEntity.ActuatorEntity actuatorEntity : device.getActuators()){
                if(actuatorEntity.getId() == actuator.getId()){
                    if(actuatorEntity.getHistory().size() >= deviceRepository.getLimit()) {
                        actuatorEntity.getHistory().remove(0);
                    }
                    log.info("found actuator on memory");
                    actuatorEntity.getHistory().add(DeviceEntity.ActuatorEntity.Status.builder().timestamp(LocalDateTime.now()).value(actuator.getState()).build());
                }
            }
        }

    }

    @PostMapping(value = "/join")
    Iot.JoinResponseMessage deviceJoinOnSystem(@RequestBody Iot.JoinRequestMessage message,  HttpServletRequest request){
      log.info("new device joining on the system");
      log.info("ip: {} port:{}", request.getRemoteAddr(), request.getRemotePort());
      //TODO: finish !!!!!!
      DeviceEntity created = deviceRepository
              .add(
                      DeviceEntity
                              .builder()
                              .uuid(UUID.randomUUID().toString())
                              .remote_address(request.getRemoteAddr())
                              .port(request.getRemotePort())
                              .name(message.getName())
                              .actuators(
                                      message.getActuatorsList().stream().map(actuator ->
                                      DeviceEntity.ActuatorEntity
                                              .builder()
                                              .history(new ArrayList<>())
                                              .id(actuator.getId())
                                              .name(actuator.getName())
                                              .build()
                                      ).collect(Collectors.toList())
                              )
                              .sensors(
                                      message.getSensorsList().stream().map(sensor ->
                                          DeviceEntity.SensorEntity
                                                  .builder()
                                                  .history(new ArrayList<>())
                                                  .id(sensor.getId())
                                                  .name(sensor.getName())
                                                  .build()
                                      ).collect(Collectors.toList())
                              )
                              .build()
              );
      if(created == null){
          log.error("error while creating device");
      }

      return Iot.JoinResponseMessage.newBuilder().setId(created.getUuid()).build();
    }
}

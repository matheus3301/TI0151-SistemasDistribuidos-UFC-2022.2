package br.ufc.smarthome.controllers;

import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.models.Iot;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/iot")
@RequiredArgsConstructor
@Slf4j
public class IotController {
    private final DeviceRepository deviceRepository;

    @PostMapping(value = "/join")
    Iot.JoinResponseMessage deviceJoinOnSystem(@RequestBody Iot.JoinRequestMessage message,  HttpServletRequest request){
      log.info("new device joining on the system");
      log.info("ip: {} port:{}", request.getRemoteAddr(), request.getRemotePort());

      //TODO: finish !!!!!! 
      deviceRepository
              .add(
                      DeviceEntity
                              .builder()
                              .uuid(UUID.randomUUID().toString())
                              .remote_address(request.getRemoteAddr())
                              .port(request.getRemotePort())
                              .name(message.getName())
                              .actuators(message.getActuatorsList().stream().map(actuator ->
                                      DeviceEntity.ActuatorEntity
                                              .builder()
                                              .history(new ArrayList<>())
                                              .id(actuator.getId())
                                              .name(actuator.getName())
                                              .build()
                              ).collect(Collectors.toList()))
                              .sensors(message.getSensorsList().stream().map(sensor ->
                                          DeviceEntity.SensorEntity
                                                  .builder()
                                                  .build()
                              ).collect(Collectors.toList()))
                              .build()
              );

      return Iot.JoinResponseMessage.newBuilder().setId(UUID.randomUUID().toString()).build();
    }
}

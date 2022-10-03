package br.ufc.smarthome.controllers;

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
import java.util.UUID;

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

      return Iot.JoinResponseMessage.newBuilder().setId(UUID.randomUUID().toString()).build();
    }
}

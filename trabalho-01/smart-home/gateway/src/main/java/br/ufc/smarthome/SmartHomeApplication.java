package br.ufc.smarthome;

import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Slf4j
public class SmartHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void searchForDevices() {
        log.info("first device discovery");
        String message = "localhost 8080";

        try {
            // TODO: set multicast group and port on application.properties

            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.0");
            byte[] buf = message.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 7777);
            socket.send(packet);
            socket.close();
        } catch (SocketException e) {
            log.error("error during datagram socket instantiation");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            log.error("error while solving datagram group name");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("error while sending multicast message");
            e.printStackTrace();
        }
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        ProtobufHttpMessageConverter protobufHttpMessageConverter = new ProtobufHttpMessageConverter();

        protobufHttpMessageConverter.getSupportedMediaTypes().forEach(
            mediaType -> {
                log.info(mediaType.getType()+"/"+mediaType.getSubtype());
            }
        );

        return protobufHttpMessageConverter;
    }

    @Bean
    DeviceRepository createDeviceRepository(){
        Map<String, DeviceEntity> map = new HashMap<>();

        return new DeviceRepository(map, 30);
    }

}

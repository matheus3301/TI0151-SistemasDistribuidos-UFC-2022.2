package br.ufc.smarthome;

import br.ufc.smarthome.entities.DeviceEntity;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Slf4j
public class SmartHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
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

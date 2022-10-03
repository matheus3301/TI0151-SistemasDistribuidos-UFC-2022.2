package br.ufc.smarthome;

import br.ufc.smarthome.models.Models;
import br.ufc.smarthome.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.InputStream;
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
        Map<String, Models.Device> map = new HashMap<>();

        Models.Device device1 = Models.Device
                .newBuilder()
                .setId("asdf098asd")
                .setName("Televisão do Quarto")
                .build();


        Models.Device device2 = Models.Device
                .newBuilder()
                .setId("hdfkl12341")
                .setName("Máquina de Lavar")
                .build();

        map.put(device1.getId(), device1);
        map.put(device2.getId(), device2);

        return new DeviceRepository(map);
    }

}

package br.gov.ufc.homeassistantserver;

import br.gov.ufc.homeassistantserver.models.Device;
import br.gov.ufc.homeassistantserver.repositories.DeviceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HomeAssistantServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeAssistantServerApplication.class, args);
    }

    @Bean
    DeviceRepository createDeviceRepository(){
        Map<String, Device> map = new HashMap<>();

        return new DeviceRepository(map, 30);
    }
}

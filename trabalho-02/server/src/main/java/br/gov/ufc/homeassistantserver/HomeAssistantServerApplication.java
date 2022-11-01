package br.gov.ufc.homeassistantserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HomeAssistantServerApplication {

    @GetMapping
    public String test(){
        return "Hello, World";
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeAssistantServerApplication.class, args);
    }

}

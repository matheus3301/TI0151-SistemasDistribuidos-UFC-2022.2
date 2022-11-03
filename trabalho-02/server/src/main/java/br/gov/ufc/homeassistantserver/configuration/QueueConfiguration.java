package br.gov.ufc.homeassistantserver.configuration;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class QueueConfiguration {
    @Bean
    public QueueName queueName(){
        return QueueName.builder().name(String.format("home-assistant.server-%s", UUID.randomUUID())).build();
    }


    @Data
    @Builder
    public static class QueueName{
        private final String name;
    }
}

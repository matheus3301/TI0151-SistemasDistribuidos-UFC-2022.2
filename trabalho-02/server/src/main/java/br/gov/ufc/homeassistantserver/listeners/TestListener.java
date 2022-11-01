package br.gov.ufc.homeassistantserver.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    @RabbitListener(queues = {"test"})
    public void test(@Payload String message){
        System.out.println(message);
    }

}

package br.gov.ufc.homeassistantserver.listeners;

import br.gov.ufc.homeassistantserver.constants.QueueNames;
import br.gov.ufc.homeassistantserver.models.Tes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeviceListener {
    @RabbitListener(queues = {QueueNames.DEVICE_STATUS_QUEUE})
    public void listenToDeviceStatus(@Payload Tes status){
        System.out.println(status.toString());
    }
}

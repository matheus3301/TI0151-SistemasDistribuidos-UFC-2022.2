package br.gov.ufc.homeassistantserver.listeners;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        message.
    }
}

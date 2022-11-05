package br.gov.ufc.homeassistantserver.configuration;

import br.gov.ufc.homeassistantserver.constants.ExchangeNames;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfiguration {
    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public FanoutExchange deviceStatusExchange(){
        return new FanoutExchange(ExchangeNames.DEVICE_STATUS_EXCHANGE, true, false);
    }

    @Bean
    public Queue deviceStatusQueue(QueueConfiguration.QueueName queueName){
        return new Queue(queueName.getName(), false, false, true);
    }

    @Bean
    public Binding fanoutExchangeBindings(
            FanoutExchange deviceStatusExchange,
            Queue processQueue
    ){
        return BindingBuilder.bind(processQueue).to(deviceStatusExchange);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        var rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}

package com.infyme.bookseat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RabbitMQConfig {

    //exchange, queue, binding

    @Value("${rabbitmq.exchange.name}")
    public String EXCHANGE_NAME;

    @Value("${rabbitmq.queue.email}")
    private String EMAIL_QUEUE;
    @Value("${rabbitmq.queue.sms}")
    private String SMS_QUEUE;
    @Value("${rabbitmq.routing-key.email}")
    public String EMAIL_KEY;
    @Value("${rabbitmq.routing-key.sms}")
    public String SMS_KEY;

    //creating queue
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    //creating queue
    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE);
    }

    //creating exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }


    //binding between exchange and email queue using routing key
    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(exchange()).with(EMAIL_KEY);
    }

    //binding between exchange and sms queue using routing key
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(exchange()).with(SMS_KEY);
    }

    //massage needs to be converted to json
    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}



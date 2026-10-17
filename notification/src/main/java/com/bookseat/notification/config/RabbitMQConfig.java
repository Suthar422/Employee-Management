package com.bookseat.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//we need json message converter
@Configuration
public class RabbitMQConfig {

//    //exchange, queue, binding
//
//    @Value("${rabbitmq.exchange.name}")
//    public String EXCHANGE_NAME;
//    @Value("${rabbitmq.queue.name}")
//    private String QUEUE_NAME;
//    @Value("${rabbitmq.routing-key.name}")
//    public String ROUTING_KEY;

    //creating queue
//    @Bean
//    public Queue queue() {
//        return new Queue(QUEUE_NAME);
//    }
//
//    //creating exchange
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//
//
//    //binding between exchange and queue using routing key
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
//    }

    //massage needs to be converted to json  - de serialize
    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}



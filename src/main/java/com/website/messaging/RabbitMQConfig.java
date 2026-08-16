package com.website.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    @Value("${notification.rabbitmq.exchange}")
    private String exchange;

    @Value("${notification.rabbitmq.queue}")
    private String queue;

    @Value("${notification.rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    TopicExchange postNotificationExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    Queue postNotificationQueue() {
        return new Queue(queue, true);
    }

    @Bean
    Binding postNotificationBinding(Queue postNotificationQueue, TopicExchange postNotificationExchange) {
        return BindingBuilder.bind(postNotificationQueue).to(postNotificationExchange).with(routingKey);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

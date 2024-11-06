package com.banking.card_account_service.config;

import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class RabbitMQConfig {
    @Value("${queue.name1}")
    private String queueName1;

    @Value("${queue.name2}")
    private String queueName2;

    @Value("${queue.name3}")
    private String queueName3;

    @Value("${queue.name4}")
    private String queueName4;

    @Value("${queue.name5}")
    private String queueName5;

    @Value("${queue.name6}")
    private String queueName6;

    @Value("${queue.name7}")
    private String queueName7;

    @Value("${queue.name8}")
    private String queueName8;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    public Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    public Queue queue3() {
        return new Queue(queueName3, false);
    }

    @Bean
    public Queue queue4() {
        return new Queue(queueName4, false);
    }

    @Bean
    public Queue queue5() {
        return new Queue(queueName5, false);
    }

    @Bean
    public Queue queue6() {
        return new Queue(queueName6, false);
    }

    @Bean
    public Queue queue7() {
        return new Queue(queueName7, false);
    }

    @Bean
    public Queue queue8() {
        return new Queue(queueName8, false);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

}


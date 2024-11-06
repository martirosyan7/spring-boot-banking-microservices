package com.banking.card_account_service.rabbitmq.sender;

import lombok.Setter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
public class MessageSender {
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendAccountMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName1, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendCardMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName2, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendAccountWithdrawalMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName3, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendAccountDepositMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName4, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendCardWithdrawalMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName5, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendCardDepositMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName6, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendAccountTransferMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName7, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    public void sendCardTransferMessage(String message) {
        try {
            amqpTemplate.convertAndSend(queueName8, message);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}

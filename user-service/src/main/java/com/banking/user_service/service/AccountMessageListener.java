package com.banking.user_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountMessageListener {

    @Autowired
    private LocalUserService localUserService;

    @RabbitListener(queues = "user-account-queue")
    public void receiveAccountMessage(String message) {
        UUID userId = UUID.fromString(message.split(" ")[0]);
        UUID accountId = UUID.fromString(message.split(" ")[1]);
        localUserService.updateUserAccounts(userId, accountId);
    }

    @RabbitListener(queues = "user-card-queue")
    public void receiveCardMessage(String message) {
        UUID userId = UUID.fromString(message.split(" ")[0]);
        UUID cardId = UUID.fromString(message.split(" ")[1]);
        localUserService.updateUserCards(userId, cardId);
    }
}

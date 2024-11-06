package com.banking.transaction_service.rabbitmq;

import com.banking.transaction_service.model.dto.TransactionDTO;
import com.banking.transaction_service.service.TransactionService;
import com.banking.transaction_service.utils.TransactionDirection;
import com.banking.transaction_service.utils.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageReceiver {

    @Autowired
    private TransactionService transactionService;

    @RabbitListener(queues = "account-withdrawal-queue")
    public void receiveAccountWithdrawalMessage(String message) {
        String number = message.split(" ")[0];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[1]));
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Account withdrawal");
        transactionDTO.setSenderNumber(number);
        transactionDTO.setRecipientNumber("");
        transactionDTO.setType(TransactionType.WITHDRAWAL);
        transactionDTO.setDirection(TransactionDirection.SEND);
        transactionService.createTransaction(transactionDTO);
    }

    @RabbitListener(queues = "account-deposit-queue")
    public void receiveAccountDepositMessage(String message) {
        String number = message.split(" ")[0];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[1]));
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Card deposit");
        transactionDTO.setSenderNumber("");
        transactionDTO.setRecipientNumber(number);
        transactionDTO.setType(TransactionType.DEPOSIT);
        transactionDTO.setDirection(TransactionDirection.RECEIVE);
        transactionService.createTransaction(transactionDTO);
    }

    @RabbitListener(queues = "card-withdrawal-queue")
    public void receiveCardWithdrawalMessage(String message) {
        String number = message.split(" ")[0];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[1]));
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Card withdrawal");
        transactionDTO.setSenderNumber(number);
        transactionDTO.setRecipientNumber("");
        transactionDTO.setType(TransactionType.WITHDRAWAL);
        transactionDTO.setDirection(TransactionDirection.SEND);
        transactionService.createTransaction(transactionDTO);
    }

    @RabbitListener(queues = "card-deposit-queue")
    public void receiveCardDepositMessage(String message) {
        String number = message.split(" ")[0];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[1]));
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Card deposit");
        transactionDTO.setSenderNumber("");
        transactionDTO.setRecipientNumber(number);
        transactionDTO.setType(TransactionType.DEPOSIT);
        transactionDTO.setDirection(TransactionDirection.RECEIVE);
        transactionService.createTransaction(transactionDTO);
    }

    @RabbitListener(queues = "account-transfer-queue")
    public void receiveAccountTransferMessage(String message) {
        String senderNumber = message.split(" ")[0];
        String recipientNumber = message.split(" ")[1];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[2]));
        String description = message.split(" ")[3];
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Account transfer");
        transactionDTO.setSenderNumber(senderNumber);
        transactionDTO.setRecipientNumber(recipientNumber);
        transactionDTO.setDescription(description);
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setDirection(TransactionDirection.SEND);
        transactionService.createTransaction(transactionDTO);
    }

    @RabbitListener(queues = "card-transfer-queue")
    public void receiveCardTransferMessage(String message) {
        String senderNumber = message.split(" ")[0];
        String recipientNumber = message.split(" ")[1];
        BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(message.split(" ")[2]));
        String description = message.split(" ")[3];
        LocalDateTime time = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(id);
        transactionDTO.setAmount(amount);
        transactionDTO.setTime(time);
        transactionDTO.setDescription("Card transfer");
        transactionDTO.setSenderNumber(senderNumber);
        transactionDTO.setRecipientNumber(recipientNumber);
        transactionDTO.setDescription(description);
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setDirection(TransactionDirection.SEND);
        transactionService.createTransaction(transactionDTO);
    }
}

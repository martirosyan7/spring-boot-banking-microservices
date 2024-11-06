package com.banking.transaction_service.model.dto.mapper;

import com.banking.transaction_service.model.Transaction;
import com.banking.transaction_service.model.dto.TransactionDTO;

public class TransactionMapper {
    public static Transaction mapToTransaction(TransactionDTO transactionDto) {
        Transaction transaction = new Transaction(
                transactionDto.getId(),
                transactionDto.getAmount(),
                transactionDto.getTime(),
                transactionDto.getDescription(),
                transactionDto.getSenderNumber(),
                transactionDto.getRecipientNumber(),
                transactionDto.getType(),
                transactionDto.getDirection()
        );
        return transaction;
    }

    public static TransactionDTO mapToTransactionDto(Transaction transaction) {
        TransactionDTO transactionDto = new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getSenderNumber(),
                transaction.getRecipientNumber(),
                transaction.getType(),
                transaction.getDirection()
        );
        return transactionDto;
    }
}

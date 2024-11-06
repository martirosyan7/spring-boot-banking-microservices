package com.banking.transaction_service.service;

import com.banking.transaction_service.model.dto.TransactionDTO;
import com.banking.transaction_service.utils.CurrencyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(UUID id);

    List<TransactionDTO> getAllTransactions();

}

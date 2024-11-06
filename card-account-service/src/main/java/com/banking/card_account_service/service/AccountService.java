package com.banking.card_account_service.service;

import com.banking.card_account_service.model.dto.AccountDTO;
import com.banking.card_account_service.utils.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountById(UUID id);
    List<AccountDTO> getAllAccounts();
    AccountDTO accountWithdraw(String number, BigDecimal amount);
    AccountDTO accountDeposit(String number, BigDecimal amount);
    AccountDTO accountTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description);
}

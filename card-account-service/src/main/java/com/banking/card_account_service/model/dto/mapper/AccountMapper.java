package com.banking.card_account_service.model.dto.mapper;

import com.banking.card_account_service.model.Account;
import com.banking.card_account_service.model.dto.AccountDTO;

public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDto) {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getBalance(),
                accountDto.getAccountNumber(),
                accountDto.getCurrencyType(),
                accountDto.getUserId()
        );
        return account;
    }

    public static AccountDTO mapToAccountDto(Account account) {
        AccountDTO accountDto = new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getCurrencyType(),
                account.getUserId()
        );
        return accountDto;
    }
}

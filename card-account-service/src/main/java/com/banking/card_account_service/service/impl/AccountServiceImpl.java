package com.banking.card_account_service.service.impl;

import com.banking.card_account_service.model.Account;
import com.banking.card_account_service.model.dto.AccountDTO;
import com.banking.card_account_service.model.dto.mapper.AccountMapper;
import com.banking.card_account_service.repository.AccountRepository;
import com.banking.card_account_service.service.AccountService;
import com.banking.card_account_service.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.mapToAccount(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDTO getAccountById(UUID id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO accountWithdraw(String number, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(number)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDTO accountDeposit(String number, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(number)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDTO accountTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description) {
        Account account1 = accountRepository.findByAccountNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account account2 = accountRepository.findByAccountNumber(recipientNumber)
                .orElseThrow(() -> new RuntimeException("Recipient account not found"));
        if (account1.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        } else {
            account1.setBalance(account1.getBalance().subtract(amount));
            account2.setBalance(account2.getBalance().add(amount));
            Account updatedAccount1 = accountRepository.save(account1);
            Account updatedAccount2 = accountRepository.save(account2);
            return AccountMapper.mapToAccountDto(updatedAccount1);
        }
    }


}

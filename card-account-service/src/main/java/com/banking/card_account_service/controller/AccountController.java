package com.banking.card_account_service.controller;

import com.auth0.jwt.JWT;
import com.banking.card_account_service.model.dto.AccountDTO;
import com.banking.card_account_service.repository.AccountRepository;
import com.banking.card_account_service.service.AccountService;
import com.banking.card_account_service.rabbitmq.sender.MessageSender;
import com.banking.card_account_service.utils.enums.CurrencyType;
import com.banking.card_account_service.utils.generator.NumberGenerator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSender messageSender;


    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AccountDTO> createAccount(@Parameter(hidden = true) @RequestHeader("authorization") String jwt,
                                                    @RequestParam CurrencyType currencyType) {
        UUID userId = getUserIdFromToken(jwt.split(" ")[1]);


        NumberGenerator numberGenerator = new NumberGenerator(accountRepository, currencyType);
        String accountNumber = numberGenerator.generateAccountNumber();

        UUID id = UUID.randomUUID();

        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(id);
        accountDto.setUserId(userId);
        accountDto.setCurrencyType(currencyType);
        accountDto.setBalance(BigDecimal.valueOf(0));
        accountDto.setAccountNumber(accountNumber);

        AccountDTO createdAccount = accountService.createAccount(accountDto);

        String message = accountDto.getUserId() + " " + accountDto.getId().toString();
        messageSender.sendAccountMessage(message);

        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AccountDTO> accountWithdraw(@RequestParam String number,
                                                      @RequestParam BigDecimal amount) {

        AccountDTO accountDto = accountService.accountWithdraw(number, amount);
        String message = number + " " + amount;
        messageSender.sendAccountWithdrawalMessage(message);

        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/deposit")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AccountDTO> accountDeposit(@RequestParam String number,
                                                     @RequestParam BigDecimal amount) {
        AccountDTO accountDto = accountService.accountDeposit(number, amount);
        String message = number + " " + amount;
        messageSender.sendAccountDepositMessage(message);

        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/transfer")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AccountDTO> accountTransfer(@RequestParam String senderNumber,
                                                      @RequestParam String recipientNumber,
                                                      @RequestParam BigDecimal amount,
                                                      @RequestParam String description) {
        AccountDTO accountDto = accountService.accountTransfer(senderNumber, recipientNumber, amount, description);
        String message = senderNumber + " " + recipientNumber + " " + amount + " " + description;
        messageSender.sendAccountTransferMessage(message);

        return ResponseEntity.ok(accountDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        AccountDTO accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(JWT.decode(token).getClaim("userId").asString());
    }
}

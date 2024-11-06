package com.banking.card_account_service.controller;


import com.auth0.jwt.JWT;
import com.banking.card_account_service.model.Card;
import com.banking.card_account_service.model.dto.AccountDTO;
import com.banking.card_account_service.model.dto.CardDTO;
import com.banking.card_account_service.repository.CardRepository;
import com.banking.card_account_service.service.CardService;
import com.banking.card_account_service.rabbitmq.sender.MessageSender;
import com.banking.card_account_service.utils.enums.CardType;
import com.banking.card_account_service.utils.enums.CurrencyType;
import com.banking.card_account_service.utils.generator.CVVGenerator;
import com.banking.card_account_service.utils.generator.NumberGenerator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@Slf4j
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private MessageSender messageSender;


    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CardDTO> createCard(@Parameter(hidden = true) @RequestHeader("authorization") String jwt,
                                              @RequestParam String pinCode,
                                              @RequestParam CardType type,
                                              @RequestParam CurrencyType currencyType) {
        UUID userId = getUserIdFromToken(jwt.split(" ")[1]);

        LocalDate now = LocalDate.now();
        LocalDate validUntil = now.plusYears(4);

        NumberGenerator numberGenerator = new NumberGenerator(cardRepository, currencyType);
        String cardNumber = numberGenerator.generateCardNumber();

        UUID id = UUID.randomUUID();

        CardDTO cardDto = new CardDTO();
        cardDto.setId(id);
        cardDto.setCardNumber(cardNumber);
        cardDto.setPinCode(pinCode);
        cardDto.setCurrency(currencyType);
        cardDto.setType(type);
        cardDto.setCvv(CVVGenerator.generateCVV(cardNumber, validUntil));
        cardDto.setValidUntil(validUntil);
        cardDto.setBalance(BigDecimal.valueOf(0));
        cardDto.setUserId(userId);

        CardDTO createdCard = cardService.createCard(cardDto);

        String message = cardDto.getUserId() + " " + cardDto.getId();
        messageSender.sendCardMessage(message);

        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @PutMapping("/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CardDTO> cardWithdraw(@RequestParam String number,
                                                   @RequestParam BigDecimal amount) {

        CardDTO cardDTO = cardService.cardWithdraw(number, amount);
        String message = number + " " + amount;
        messageSender.sendCardWithdrawalMessage(message);

        return ResponseEntity.ok(cardDTO);
    }

    @PutMapping("/deposit")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CardDTO> cardDeposit(@RequestParam String number,
                                                   @RequestParam BigDecimal amount) {

        CardDTO cardDTO = cardService.cardDeposit(number, amount);
        String message = number + " " + amount;
        messageSender.sendCardDepositMessage(message);

        return ResponseEntity.ok(cardDTO);
    }

    @PutMapping("/transfer")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CardDTO> cardTransfer(@RequestParam String senderNumber,
                                             @RequestParam String recipientNumber,
                                             @RequestParam BigDecimal amount,
                                             @RequestParam String description) {
        CardDTO cardDto = cardService.cardTransfer(senderNumber, recipientNumber, amount, description);
        String message = senderNumber + " " + recipientNumber + " " + amount + " " + description;
        messageSender.sendCardTransferMessage(message);

        return ResponseEntity.ok(cardDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID id) {
        CardDTO cardDto = cardService.getCardById(id);
        return ResponseEntity.ok(cardDto);
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(JWT.decode(token).getClaim("userId").asString());
    }
}

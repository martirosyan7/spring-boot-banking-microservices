package com.banking.card_account_service.service;

import com.banking.card_account_service.model.dto.CardDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CardService {
    CardDTO createCard(CardDTO cardDTO);
    CardDTO getCardById(UUID id);
    List<CardDTO> getAllCards();
    CardDTO cardWithdraw(String number, BigDecimal amount);
    CardDTO cardDeposit(String number, BigDecimal amount);
    CardDTO cardTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description);
}

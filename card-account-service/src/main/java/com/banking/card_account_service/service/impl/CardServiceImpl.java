package com.banking.card_account_service.service.impl;

import com.banking.card_account_service.model.Card;
import com.banking.card_account_service.model.dto.CardDTO;
import com.banking.card_account_service.model.dto.mapper.CardMapper;
import com.banking.card_account_service.repository.CardRepository;
import com.banking.card_account_service.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;


    @Override
    public CardDTO createCard(CardDTO cardDTO) {
        Card card = CardMapper.mapToCard(cardDTO);
        Card savedCard = cardRepository.save(card);
        return CardMapper.mapToCardDto(savedCard);
    }

    @Override
    public CardDTO getCardById(UUID id) {
        Card card = cardRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));
        return CardMapper.mapToCardDto(card);
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map((card) -> CardMapper.mapToCardDto(card))
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO cardWithdraw(String number, BigDecimal amount) {
        Card card = cardRepository.findByCardNumber(number)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));

        if (card.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        } else {
            card.setBalance(card.getBalance().subtract(amount));
            Card savedCard = cardRepository.save(card);

            return CardMapper.mapToCardDto(savedCard);
        }
    }

    @Override
    public CardDTO cardDeposit(String number, BigDecimal amount) {
        Card card = cardRepository.findByCardNumber(number)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));

        card.setBalance(card.getBalance().add(amount));
        Card savedCard = cardRepository.save(card);

        return CardMapper.mapToCardDto(savedCard);
    }

    @Override
    public CardDTO cardTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description) {
        Card senderCard = cardRepository.findByCardNumber(senderNumber)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));

        Card recipientCard = cardRepository.findByCardNumber(recipientNumber)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));

        if (senderCard.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        } else {
            senderCard.setBalance(senderCard.getBalance().subtract(amount));
            recipientCard.setBalance(recipientCard.getBalance().add(amount));

            Card savedSenderCard = cardRepository.save(senderCard);
            Card savedRecipientCard = cardRepository.save(recipientCard);

            return CardMapper.mapToCardDto(savedSenderCard);
        }
    }
}

package com.banking.card_account_service.model.dto.mapper;

import com.banking.card_account_service.model.Card;
import com.banking.card_account_service.model.dto.CardDTO;

public class CardMapper {
    public static Card mapToCard(CardDTO cardDto) {
        Card card = new Card(
                cardDto.getId(),
                cardDto.getCardNumber(),
                cardDto.getBalance(),
                cardDto.getPinCode(),
                cardDto.getValidUntil(),
                cardDto.getCvv(),
                cardDto.getType(),
                cardDto.getCurrency(),
                cardDto.getUserId()
        );
        return card;
    }

    public static CardDTO mapToCardDto(Card card) {
        CardDTO cardDto = new CardDTO(
                card.getId(),
                card.getCardNumber(),
                card.getBalance(),
                card.getPinCode(),
                card.getValidUntil(),
                card.getCvv(),
                card.getType(),
                card.getCurrencyType(),
                card.getUserId()
        );
        return cardDto;
    }
}

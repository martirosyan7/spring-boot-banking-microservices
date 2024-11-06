package com.banking.card_account_service.model.dto;

import com.banking.card_account_service.utils.enums.CardType;
import com.banking.card_account_service.utils.enums.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO implements Serializable {

    private UUID id;
    private String cardNumber;
    private BigDecimal balance;
    private String pinCode;
    private LocalDate validUntil;
    private String cvv;
    private CardType type;
    private CurrencyType currency;
    private UUID userId;


}

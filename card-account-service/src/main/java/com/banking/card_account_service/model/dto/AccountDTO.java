package com.banking.card_account_service.model.dto;

import com.banking.card_account_service.utils.enums.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO implements Serializable {
    private UUID id;
    private BigDecimal balance;
    private String accountNumber;
    private CurrencyType currencyType;
    private UUID userId;

}

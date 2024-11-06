package com.banking.transaction_service.model.dto;

import com.banking.transaction_service.utils.CurrencyType;
import com.banking.transaction_service.utils.TransactionDirection;
import com.banking.transaction_service.utils.TransactionStatus;
import com.banking.transaction_service.utils.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private UUID id;
    private BigDecimal amount;
    private LocalDateTime time;
    private String description;
    private String senderNumber;
    private String recipientNumber;
    private TransactionType type;
    private TransactionDirection direction;

}

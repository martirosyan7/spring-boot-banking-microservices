package com.banking.transaction_service.model;

import com.banking.transaction_service.utils.CurrencyType;
import com.banking.transaction_service.utils.TransactionDirection;
import com.banking.transaction_service.utils.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import com.banking.transaction_service.utils.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "time", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    @Column(name = "description")
    private String description;

    @Column(name = "sender_number", nullable = false)
    private String senderNumber;

    @Column(name = "recipient_number", nullable = false)
    private String recipientNumber;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "direction", nullable = false)
    private TransactionDirection direction;
}
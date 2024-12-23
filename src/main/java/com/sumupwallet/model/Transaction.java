package com.sumupwallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sumupwallet.config.LocalDateTimeSerializer;
import com.sumupwallet.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdOn;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}

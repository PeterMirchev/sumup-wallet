package com.sumupwallet.model.dto;

import com.sumupwallet.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
@Builder
public class WalletResponse {

    private UUID id;
    private String walletName;
    private Currency currency;
    private BigDecimal balance;
    private Transaction transaction;
    private UserResponse user;
}

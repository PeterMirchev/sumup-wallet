package com.sumupwallet.dto;

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
    private UserResponse user;
}

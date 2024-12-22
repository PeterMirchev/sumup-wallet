package com.sumupwallet.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
@Builder
public class WalletsResponse {

    private UUID id;
    private String walletName;
    private Currency currency;
    private BigDecimal balance;
}

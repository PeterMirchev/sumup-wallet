package com.sumupwallet.dto;

import com.sumupwallet.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WalletTransactionsResponse {

    private UUID id;
    private String walletName;
    private Currency currency;
    private BigDecimal balance;
    private List<Transaction> transactions = new ArrayList<>();
    private UserResponse user;
}

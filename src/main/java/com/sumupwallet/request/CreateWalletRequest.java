package com.sumupwallet.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;

@Data
@Builder
public class CreateWalletRequest {

    @NotNull(message = "wallet name required")
    private String walletName;

    @NotNull(message = "Currency required")
    private Currency currency;
}

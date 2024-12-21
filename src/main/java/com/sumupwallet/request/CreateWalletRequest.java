package com.sumupwallet.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;

@Data
@Builder
public class CreateWalletRequest {

    @NotNull(message = "wallet name required")
    @Size(min = 2)
    private String walletName;

    @NotNull(message = "Currency required")
    private Currency currency;
}

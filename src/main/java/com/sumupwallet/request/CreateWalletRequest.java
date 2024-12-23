package com.sumupwallet.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletRequest {

    @NotNull(message = "wallet name required")
    @Size(min = 2)
    private String walletName;

    @NotNull(message = "Currency is required")
    @Pattern(regexp = "^(EUR|USD|GBP|AUD|JPY|CAD|CHF|CNY|INR|MXN|BRL|NZD|SEK|SGD|HKD|NOK|RUB|ZAR|TRY|KRW)$",
            message = "Invalid currency code.")
    private String currency;

}

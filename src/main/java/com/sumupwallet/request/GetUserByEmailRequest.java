package com.sumupwallet.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserByEmailRequest {

    @NotNull(message = "email is required")
    private String email;
}

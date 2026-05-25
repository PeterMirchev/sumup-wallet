package com.sumupwallet.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateUserRequest {

    @NotNull
    private UUID userId;
    @Size(min = 2)
    @NotNull(message = "firstName required")
    private String firstName;
    @Size(min = 2)
    @NotNull(message = "lastName required")
    private String lastName;
}

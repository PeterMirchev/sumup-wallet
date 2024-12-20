package com.sumupwallet.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    @Size(min = 2)
    @NotNull(message = "firstName required")
    private String firstName;
    @Size(min = 2)
    @NotNull(message = "lastName required")
    private String lastName;
    @NotNull(message = "password required")
    private String password;
    @Email(message = "invalid Email")
    private String email;



}

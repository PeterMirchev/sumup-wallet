package com.sumupwallet.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class JwtResponse {
    private UUID id;
    private String token;


    public JwtResponse(UUID id, String token) {
        this.id = id;
        this.token = token;
    }
}
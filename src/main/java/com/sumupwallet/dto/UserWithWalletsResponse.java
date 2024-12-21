package com.sumupwallet.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserWithWalletsResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private List<WalletResponse> wallets = new ArrayList<>();
}

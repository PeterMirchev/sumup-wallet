package com.sumupwallet.mapper;

import com.sumupwallet.dto.WalletResponse;
import com.sumupwallet.dto.WalletsResponse;
import com.sumupwallet.model.Wallet;
import com.sumupwallet.request.CreateWalletRequest;

import java.math.BigDecimal;
import java.util.List;

public class WalletMapper {

    public static Wallet mapToWallet(CreateWalletRequest request) {

        return Wallet.builder()
                .walletName(request.getWalletName())
                .currency(request.getCurrency())
                .balance(BigDecimal.valueOf(0))
                .build();
    }

    public static WalletResponse mapToWalletResponse(Wallet wallet) {

        return WalletResponse.builder()
                .id(wallet.getId())
                .walletName(wallet.getWalletName())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .user(UserMapper.mapToUserResponseDto(wallet.getUser()))
                .build();
    }

    public static List<WalletsResponse> mapToWalletsResponse(List<Wallet> wallets) {

        return wallets.stream()
                .map(wallet -> WalletsResponse.builder()
                        .id(wallet.getId())
                        .walletName(wallet.getWalletName())
                        .currency(wallet.getCurrency())
                        .balance(wallet.getBalance())
                        .build())
                .toList();
    }
}

package com.sumupwallet.utils.mapper;

import com.sumupwallet.model.dto.WalletResponse;
import com.sumupwallet.model.dto.WalletTransactionsResponse;
import com.sumupwallet.model.dto.WalletsResponse;
import com.sumupwallet.model.Wallet;
import com.sumupwallet.request.CreateWalletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class WalletMapper {

    public static Wallet mapToWallet(CreateWalletRequest request) {

        return Wallet.builder()
                .walletName(request.getWalletName())
                .currency(Currency.getInstance(request.getCurrency()))
                .balance(BigDecimal.valueOf(0))
                .transactions(new ArrayList<>())
                .build();
    }

    public static WalletResponse mapToWalletResponse(Wallet wallet) {

        return WalletResponse.builder()
                .id(wallet.getId())
                .walletName(wallet.getWalletName())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .transaction(wallet.getTransactions().isEmpty() ? null : wallet.getTransactions().get(wallet.getTransactions().size() - 1))
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

    public static WalletTransactionsResponse mapToWalletWithTransactionsResponse(Wallet wallet) {

        return WalletTransactionsResponse.builder()
                .id(wallet.getId())
                .walletName(wallet.getWalletName())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .transactions(wallet.getTransactions())
                .user(UserMapper.mapToUserResponseDto(wallet.getUser()))
                .build();
    }
}

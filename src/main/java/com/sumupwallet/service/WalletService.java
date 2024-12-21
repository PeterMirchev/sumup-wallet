package com.sumupwallet.service;

import com.sumupwallet.model.Wallet;
import com.sumupwallet.request.CreateWalletRequest;
import com.sumupwallet.request.UpdateWalletRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public interface WalletService {

    Wallet getWalletById(UUID id);
    Wallet createWallet(CreateWalletRequest request, UUID userId);
    Wallet updateWallet(UpdateWalletRequest walletUpdateRequest, UUID id);
    Wallet depositMoney(UUID id, BigDecimal amount);
    Wallet withdrawMoney(UUID id, BigDecimal amount);
    void deleteWallet(UUID id);

    List<Wallet> getAllWalletsByUserId(UUID userId);
}

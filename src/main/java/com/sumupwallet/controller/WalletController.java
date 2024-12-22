package com.sumupwallet.controller;

import com.sumupwallet.model.dto.WalletResponse;
import com.sumupwallet.model.dto.WalletTransactionsResponse;
import com.sumupwallet.model.dto.WalletsResponse;
import com.sumupwallet.utils.mapper.WalletMapper;
import com.sumupwallet.model.Wallet;
import com.sumupwallet.request.CreateWalletRequest;
import com.sumupwallet.response.ApiResponse;
import com.sumupwallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create/")
    public ResponseEntity<ApiResponse> createWallet(@RequestBody @Valid CreateWalletRequest request, @RequestParam UUID userId) {

        Wallet wallet = walletService.createWallet(request, userId);

        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully created with name: %s".formatted(request.getWalletName()), response));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateWallet(@RequestParam(name = "walletId") UUID walletId, @RequestParam(name = "walletName") String walletName) {

        Wallet wallet = walletService.updateWallet(walletId, walletName);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully updated with new name: %s", response));
    }

    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse> depositMoney(@RequestParam(name = "walletId") UUID walletId, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.depositMoney(walletId, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Successfully deposited: %s %s".formatted(amount, wallet.getCurrency()), response));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawMoney(@RequestParam(name = "walletId") UUID walletId, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.withdrawMoney(walletId, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Successfully withdrawn: %s %s".formatted(amount, wallet.getCurrency()), response));
    }

    @GetMapping("/all-wallets/{userId}")
    public ResponseEntity<ApiResponse> getWallets(@PathVariable UUID userId) {

        List<Wallet> wallets = walletService.getAllWalletsByUserId(userId);
        List<WalletsResponse> responses = WalletMapper.mapToWalletsResponse(wallets);

        return ResponseEntity.ok(new ApiResponse("Wallets Successfully retrieved: ", responses));
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<ApiResponse> getWalletById(@PathVariable UUID walletId) {

        Wallet wallet = walletService.getWalletById(walletId);
        WalletTransactionsResponse response = WalletMapper.mapToWalletWithTransactionsResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet Successfully retrieved: ", response));
    }

    @GetMapping("/balance/{walletId}")
    public ResponseEntity<ApiResponse> getWalletBalance(@PathVariable UUID walletId) {

        BigDecimal balance = walletService.getWalletBalance(walletId);

        return ResponseEntity.ok(new ApiResponse("Wallet Balance retrieved:", balance));
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<ApiResponse> deleteWallet(@PathVariable(name = "walletId") UUID walletId) {

        walletService.deleteWallet(walletId);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully deleted!", null));
    }
}

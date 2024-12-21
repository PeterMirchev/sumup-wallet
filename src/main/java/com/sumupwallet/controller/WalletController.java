package com.sumupwallet.controller;

import com.sumupwallet.dto.WalletResponse;
import com.sumupwallet.dto.WalletsResponse;
import com.sumupwallet.mapper.WalletMapper;
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

    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse> createWallet(@RequestBody @Valid CreateWalletRequest request, @PathVariable UUID userId) {

        Wallet wallet = walletService.createWallet(request, userId);

        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully created with name: %s", response));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateWallet(@RequestParam(name = "id") UUID id,
                                                    @RequestParam(name = "name") String name) {

        Wallet wallet = walletService.updateWallet(id, name);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully updated with new name: %s", response));
    }

    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse> depositMoney(@RequestParam(name = "id") UUID id, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.depositMoney(id, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Successfully deposited: %s".formatted(amount), response));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawMoney(@RequestParam(name = "id") UUID id, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.withdrawMoney(id, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Successfully withdrawn: %s".formatted(amount), response));
    }

    @GetMapping("/all-wallets/{userId}")
    public ResponseEntity<ApiResponse> getWallets(@PathVariable UUID userId) {

        List<Wallet> wallets = walletService.getAllWalletsByUserId(userId);
        List<WalletsResponse> responses = WalletMapper.mapToWalletsResponse(wallets);

        return ResponseEntity.ok(new ApiResponse("Wallets Successfully retrieved: ", responses));
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<ApiResponse> getWalletById(@PathVariable UUID id) {

        Wallet wallet = walletService.getWalletById(id);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse("Wallet Successfully retrieved: ", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWallet(@PathVariable(name = "id") UUID id) {

        walletService.deleteWallet(id);

        return ResponseEntity.ok(new ApiResponse("Wallet successfully deleted!", null));
    }
}

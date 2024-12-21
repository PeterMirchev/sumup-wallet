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

        return ResponseEntity.ok(new ApiResponse("Wallet Successfully created: ", response));
    }

    @GetMapping("/all-wallets/{userId}")
    public ResponseEntity<ApiResponse> getWallets(@PathVariable UUID userId) {

        List<Wallet> wallets = walletService.getAllWalletsByUserId(userId);
        List<WalletsResponse> responses = WalletMapper.mapToWalletsResponse(wallets);

        return ResponseEntity.ok(new ApiResponse("Wallets Successfully retrieved: ", responses));
    }
}

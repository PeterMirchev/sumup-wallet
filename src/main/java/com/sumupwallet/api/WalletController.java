package com.sumupwallet.api;

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

import static com.sumupwallet.utils.CommonMessages.*;
import static com.sumupwallet.utils.mapper.Endpoints.WALLETS_CONTROLLER;

@RestController
@RequestMapping(WALLETS_CONTROLLER)
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Creates a new wallet for a user.
     * Endpoint: POST http://localhost:8080/api/v1/wallets
     * @param request The wallet creation details (userId, walletName, currency).
     * @return ResponseEntity with the created wallet details.
     */
    @PostMapping()
    public ResponseEntity<ApiResponse> createWallet(@RequestBody @Valid CreateWalletRequest request) {

        Wallet wallet = walletService.createWallet(request);

        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse(WALLET_CREATED.formatted(request.getWalletName()), response));
    }

    /**
     * Updates an existing wallet's name.
     * Endpoint: PUT http://localhost:8080/api/v1/wallets?walletId={walletId}&walletName={walletName}
     * @param walletId The ID of the wallet to update.
     * @param walletName The new name for the wallet.
     * @return ResponseEntity with the updated wallet details.
     */
    @PutMapping()
    public ResponseEntity<ApiResponse> updateWallet(@RequestParam(name = "walletId") UUID walletId, @RequestParam(name = "walletName") String walletName) {

        Wallet wallet = walletService.updateWallet(walletId, walletName);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse(WALLET_UPDATED, response));
    }

    /**
     * Deposits money into a wallet.
     * Endpoint: PUT http://localhost:8080/api/v1/wallets/deposit?walletId={walletId}&amount={amount}
     * @param walletId The ID of the wallet.
     * @param amount The amount to deposit.
     * @return ResponseEntity with the updated wallet details.
     */
    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse> depositMoney(@RequestParam(name = "walletId") UUID walletId, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.depositMoney(walletId, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse(SUCCESSFULLY_DEPOSITED.formatted(amount, wallet.getCurrency()), response));
    }

    /**
     * Withdraws money from a wallet.
     * Endpoint: PUT http://localhost:8080/api/v1/wallets/withdraw?walletId={walletId}&amount={amount}
     * @param walletId The ID of the wallet.
     * @param amount The amount to withdraw.
     * @return ResponseEntity with the updated wallet details.
     */
    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawMoney(@RequestParam(name = "walletId") UUID walletId, @RequestParam BigDecimal amount) {

        Wallet wallet = walletService.withdrawMoney(walletId, amount);
        WalletResponse response = WalletMapper.mapToWalletResponse(wallet);

        return ResponseEntity.ok(new ApiResponse(SUCCESSFULLY_WITHDRAWN.formatted(amount, wallet.getCurrency()), response));
    }

    /**
     * Retrieves all wallets belonging to a user.
     * Endpoint: GET http://localhost:8080/api/v1/wallets/user/{userId}
     * @param userId The ID of the user.
     * @return ResponseEntity with a list of user's wallets.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getWallets(@PathVariable UUID userId) {

        List<Wallet> wallets = walletService.getAllWalletsByUserId(userId);
        List<WalletsResponse> responses = WalletMapper.mapToWalletsResponse(wallets);

        return ResponseEntity.ok(new ApiResponse(WALLET_RETRIEVED, responses));
    }

    /**
     * Retrieves a wallet by its ID.
     * Endpoint: GET http://localhost:8080/api/v1/wallets/{walletId}
     * @param walletId The ID of the wallet.
     * @return ResponseEntity with the wallet details and transactions.
     */
    @GetMapping("/{walletId}")
    public ResponseEntity<ApiResponse> getWalletById(@PathVariable UUID walletId) {

        Wallet wallet = walletService.getWalletById(walletId);
        WalletTransactionsResponse response = WalletMapper.mapToWalletWithTransactionsResponse(wallet);

        return ResponseEntity.ok(new ApiResponse(WALLET_RETRIEVED, response));
    }

    /**
     * Retrieves the balance of a wallet.
     * Endpoint: GET http://localhost:8080/api/v1/wallets/balance/{walletId}
     * @param walletId The ID of the wallet.
     * @return ResponseEntity with the current balance.
     */
    @GetMapping("/balance/{walletId}")
    public ResponseEntity<ApiResponse> getWalletBalance(@PathVariable UUID walletId) {

        BigDecimal balance = walletService.getWalletBalance(walletId);

        return ResponseEntity.ok(new ApiResponse(WALLET_BALANCE_RETRIEVED, balance));
    }

    /**
     * Deletes a wallet.
     * Endpoint: DELETE http://localhost:8080/api/v1/wallets/{walletId}
     * @param walletId The ID of the wallet to delete.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/{walletId}")
    public ResponseEntity<ApiResponse> deleteWallet(@PathVariable UUID walletId) {

        walletService.deleteWallet(walletId);

        return ResponseEntity.ok(new ApiResponse(WALLET_DELETED, null));
    }
}

package com.sumupwallet.api;

import com.sumupwallet.model.Transaction;
import com.sumupwallet.response.ApiResponse;
import com.sumupwallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.sumupwallet.utils.CommonMessages.*;
import static com.sumupwallet.utils.mapper.Endpoints.TRANSACTIONS_CONTROLLER;

@RestController
@RequestMapping(TRANSACTIONS_CONTROLLER)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    /**
     * Retrieves all transactions for a specific wallet.
     * Endpoint: GET /transactions/wallet/{walletId}
     * @param walletId The ID of the wallet.
     * @return ResponseEntity with a list of transactions.
     */
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<ApiResponse> getTransactions(@PathVariable UUID walletId) {

        List<Transaction> transactions = transactionService.getAllTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_TRANSACTIONS.formatted(transactions.size()), transactions));
    }

    /**
     * Retrieves all deposit transactions for a specific wallet.
     * Endpoint: GET /transactions/deposit?walletId={walletId}
     * @param walletId The ID of the wallet.
     * @return ResponseEntity with a list of deposit transactions.
     */
    @GetMapping("/deposit")
    public ResponseEntity<ApiResponse> getAllDepositTransactions(@RequestParam("walletId") UUID walletId) {

        List<Transaction> transactions = transactionService.getAllDepositTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_DEPOSITED_TRANSACTIONS.formatted(transactions.size()), transactions));
    }

    /**
     * Retrieves all withdrawal transactions for a specific wallet.
     * Endpoint: GET /transactions/withdrawal?walletId={walletId}
     * @param walletId The ID of the wallet.
     * @return ResponseEntity with a list of withdrawal transactions.
     */
    @GetMapping("/withdrawal")
    public ResponseEntity<ApiResponse> getAllWithdrawalTransactions(@RequestParam("walletId") UUID walletId) {

        List<Transaction> transactions = transactionService.getAllWithdrawalTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_WITHDRAWN_TRANSACTIONS.formatted(transactions.size()), transactions));
    }
}

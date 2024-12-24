package com.sumupwallet.controller;

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

    @GetMapping("/all/{walletId}")
    public ResponseEntity<ApiResponse> getTransactions(@PathVariable(name = "walletId") UUID walletId) {

        List<Transaction> transactions = transactionService.getAllTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_TRANSACTIONS.formatted(transactions.size()), transactions));
    }

    @GetMapping("/all/deposit")
    public ResponseEntity<ApiResponse> getAllDepositTransactions(@RequestParam("walletId") UUID walletId) {

        List<Transaction> transactions = transactionService.getAllDepositTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_DEPOSITED_TRANSACTIONS.formatted(transactions.size()), transactions));
    }

    @GetMapping("/all/withdrawal")
    public ResponseEntity<ApiResponse> getAllWithdrawalTransactions(@RequestParam("walletId") UUID walletId) {

        List<Transaction> transactions = transactionService.getAllWithdrawalTransactions(walletId);

        return ResponseEntity.ok(new ApiResponse(TOTAL_WITHDRAWN_TRANSACTIONS.formatted(transactions.size()), transactions));
    }
}

package com.sumupwallet.service;

import com.sumupwallet.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions(UUID walletId);
    List<Transaction> getAllDepositTransactions(UUID walletId);
    List<Transaction> getAllWithdrawalTransactions(UUID walletId);
}

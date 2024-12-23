package com.sumupwallet.service.impl;

import com.sumupwallet.model.Transaction;
import com.sumupwallet.repository.TransactionRepository;
import com.sumupwallet.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAllTransactions(UUID walletId) {

        List<Transaction> allByWalletId = transactionRepository.findAllByWalletId(walletId);

        System.out.println(allByWalletId);
        return allByWalletId;
    }

    @Override
    public List<Transaction> getAllDepositTransactions(UUID walletId) {

        return transactionRepository.getAllDepositTransactions(walletId);
    }

    @Override
    public List<Transaction> getAllWithdrawalTransactions(UUID walletId) {

        return transactionRepository.getAllWithdrawalTransactions(walletId);
    }
}

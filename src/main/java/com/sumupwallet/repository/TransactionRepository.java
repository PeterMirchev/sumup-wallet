package com.sumupwallet.repository;

import com.sumupwallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllByWalletId(UUID walletId);

    @Query("""
    SELECT t FROM Transaction t
    WHERE t.wallet.id = :walletId
    AND t.type = "DEPOSIT"
   """)
    List<Transaction> getAllDepositTransactions(UUID walletId);

    @Query("""
    SELECT t FROM Transaction t
    WHERE t.wallet.id = :walletId
    AND t.type = "WITHDRAWAL"
   """)
    List<Transaction> getAllWithdrawalTransactions(UUID walletId);
}

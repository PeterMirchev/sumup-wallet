package com.sumupwallet.repository;

import com.sumupwallet.model.Transaction;
import com.sumupwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findWalletByUserId(UUID userId);

    @Query("""
        SELECT t FROM Transaction t
        JOIN Wallet w where w.id = :walletId
        AND t.type = "DEPOSIT"
        """)
    List<Transaction> getAllDepositTransactions(UUID walletId);

    @Query("""
        SELECT t FROM Transaction t
        JOIN Wallet w where w.id = :walletId
        AND t.type = "WITHDRAWAL"
        """)
    List<Transaction> getAllWithdrawalTransactions(UUID walletId);
}

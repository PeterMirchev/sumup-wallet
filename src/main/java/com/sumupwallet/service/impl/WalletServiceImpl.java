package com.sumupwallet.service.impl;

import com.sumupwallet.utils.enums.TransactionType;
import com.sumupwallet.exception.InvalidAmountException;
import com.sumupwallet.exception.ResourceAlreadyExistException;
import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.utils.mapper.WalletMapper;
import com.sumupwallet.utils.mapper.TransactionMapper;
import com.sumupwallet.model.Transaction;
import com.sumupwallet.model.User;
import com.sumupwallet.model.Wallet;
import com.sumupwallet.repository.TransactionRepository;
import com.sumupwallet.repository.UserRepository;
import com.sumupwallet.repository.WalletRepository;
import com.sumupwallet.request.CreateWalletRequest;
import com.sumupwallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.sumupwallet.utils.ExceptionMessages.*;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(WalletRepository walletRepository,
                             UserRepository userRepository,
                             TransactionRepository transactionRepository) {

        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Wallet getWalletById(UUID id) {

        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));
    }

    @Override
    public Wallet createWallet(CreateWalletRequest request, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID_NOT_FOUND.formatted(userId.toString())));

        user.getWallets()
                .forEach(wallet -> {
                    if (wallet.getWalletName().equals(request.getWalletName())) {
                        throw new ResourceAlreadyExistException(WALLET_ALREADY_EXIST.formatted(request.getWalletName()));
                    }});

        Wallet wallet = WalletMapper.mapToWallet(request);
        wallet.setUser(user);

        user.getWallets().add(wallet);

        walletRepository.save(wallet);
        userRepository.save(user);

        return wallet;
    }

    @Override
    public Wallet updateWallet(UUID id, String name) {

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));

        wallet.setWalletName(name);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet depositMoney(UUID id, BigDecimal amount) {

        if (amount.signum() <= 0) {
            throw new InvalidAmountException(AMOUNT_EXCEPTION.formatted(amount));
        }

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));

        Transaction transaction = TransactionMapper.mapToTransaction(amount, TransactionType.DEPOSIT);
        Transaction persistedTransaction = transactionRepository.save(transaction);

        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.getTransactions().add(persistedTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet withdrawMoney(UUID id, BigDecimal amount) {

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(AMOUNT_EXCEPTION.formatted(amount));
        }

        if (amount.compareTo(wallet.getBalance()) > 0) {
            throw new InvalidAmountException(INSUFFICIENT_FUNDS.formatted(amount, wallet.getBalance()));
        }
        Transaction transaction = TransactionMapper.mapToTransaction(amount, TransactionType.WITHDRAWAL);
        Transaction persistedTransaction = transactionRepository.save(transaction);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.getTransactions().add(persistedTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void deleteWallet(UUID id) {

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));

        User user = wallet.getUser();
        if (user != null) {
            user.getWallets().remove(wallet);
        }

        walletRepository.delete(wallet);
    }

    @Override
    public List<Wallet> getAllWalletsByUserId(UUID userId) {

        return walletRepository.findWalletByUserId(userId);
    }

    @Override
    public BigDecimal getWalletBalance(UUID id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WALLET_WITH_ID_NOT_FOUND.formatted(id)));

        return wallet.getBalance();
    }
}

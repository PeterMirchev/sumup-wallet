package com.sumupwallet.service.impl;

import com.sumupwallet.exception.ResourceAlreadyExistException;
import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.mapper.WalletMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.model.Wallet;
import com.sumupwallet.repository.UserRepository;
import com.sumupwallet.repository.WalletRepository;
import com.sumupwallet.request.CreateWalletRequest;
import com.sumupwallet.request.UpdateWalletRequest;
import com.sumupwallet.service.UserService;
import com.sumupwallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserService userService, UserRepository userRepository) {

        this.walletRepository = walletRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Wallet getWalletById(UUID id) {

        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet Not Found! Invalid Wallet ID - %s".formatted(id.toString())));
    }

    @Override
    public Wallet createWallet(CreateWalletRequest request, UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found! Invalid user id: %s".formatted(userId.toString())));

        user.getWallets()
                .forEach(wallet -> {
                    if (wallet.getWalletName().equals(request.getWalletName())) {
                        throw new ResourceAlreadyExistException("Wallet Name Already Exist: {%s}".formatted(request.getWalletName()));
                    }});

        Wallet wallet = WalletMapper.mapToWallet(request);
        wallet.setUser(user);

        user.getWallets().add(wallet);

        walletRepository.save(wallet);
        userRepository.save(user);

        return wallet;
    }

    @Override
    public Wallet updateWallet(UpdateWalletRequest walletUpdateRequest, UUID id) {
        return null;
    }

    @Override
    public Wallet depositMoney(UUID id, BigDecimal amount) {
        return null;
    }

    @Override
    public Wallet withdrawMoney(UUID id, BigDecimal amount) {
        return null;
    }

    @Override
    public void deleteWallet(UUID id) {

    }

    @Override
    public List<Wallet> getAllWalletsByUserId(UUID userId) {

        return walletRepository.findWalletByUserId(userId);
    }
}

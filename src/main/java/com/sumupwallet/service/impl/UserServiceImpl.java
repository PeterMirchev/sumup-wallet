package com.sumupwallet.service.impl;

import com.sumupwallet.exception.ResourceAlreadyExistException;
import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.service.EmailService;
import com.sumupwallet.utils.mapper.UserMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.repository.UserRepository;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.request.UpdateUserRequest;
import com.sumupwallet.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.sumupwallet.utils.CommonMessages.*;
import static com.sumupwallet.utils.ExceptionMessages.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistException(EMAIL_EXISTS + request.getEmail());
        }

        User user = UserMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailService.sendEmail(user.getEmail(), WELCOME_MESSAGE, SUCCESSFULLY_REGISTERED_EMAIL_BODY.formatted(request.getFirstName()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest request, UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID_NOT_FOUND.formatted(id)));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID_NOT_FOUND.formatted(id)));
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_EMAIL_NOT_FOUND.formatted(email)));
    }

    @Override
    public void deleteUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_ID_NOT_FOUND.formatted(id)));

        userRepository.delete(user);

        if (userRepository.findById(id).isEmpty()) {
            emailService.sendEmail(user.getEmail(), ACCOUNT_DEACTIVATION, SUCCESSFULLY_DELETED_EMAIL_BODY.formatted(user.getFirstName()));
        }
    }
}

package com.sumupwallet.service.impl;

import com.sumupwallet.exception.ResourceAlreadyExistException;
import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.mapper.UserMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.repository.UserRepository;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.request.UpdateUserRequest;
import com.sumupwallet.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistException("Email already registered: " + request.getEmail());
        }

        User user = UserMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest request, UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found! Invalid user id:\n%s".formatted(id.toString())));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID id) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }
}

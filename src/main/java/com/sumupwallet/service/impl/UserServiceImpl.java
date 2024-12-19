package com.sumupwallet.service.impl;

import com.sumupwallet.exception.EmailAlreadyExistException;
import com.sumupwallet.mapper.UserMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.repository.UserRepository;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.request.UpdateUserRequest;
import com.sumupwallet.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Email already registered: " + request.getEmail());
        }

        User user = UserMapper.mapToUser(request);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserRequest request) {
        return null;
    }

    @Override
    public User getUser(UUID id) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }
}

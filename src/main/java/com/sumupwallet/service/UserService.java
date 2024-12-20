package com.sumupwallet.service;

import com.sumupwallet.model.User;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.request.UpdateUserRequest;

import java.util.UUID;


public interface UserService {

    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, UUID id);
    User getUser(UUID id);
    void deleteUser(UUID id);
}

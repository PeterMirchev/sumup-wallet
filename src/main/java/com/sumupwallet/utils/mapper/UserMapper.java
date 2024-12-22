package com.sumupwallet.utils.mapper;

import com.sumupwallet.model.dto.UserResponse;
import com.sumupwallet.utils.enums.Role;
import com.sumupwallet.model.User;
import com.sumupwallet.request.CreateUserRequest;

import java.util.Set;

public class UserMapper {


    public static User mapToUser(CreateUserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .email(request.getEmail())
                .roles(Set.of(Role.USER))
                .build();
    }

    public static UserResponse mapToUserResponseDto(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

package com.sumupwallet.mapper;

import com.sumupwallet.dto.UserResponseDto;
import com.sumupwallet.model.User;
import com.sumupwallet.request.CreateUserRequest;

public class UserMapper {


    public static User mapToUser(CreateUserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

    public static UserResponseDto mapToUserResponseDto(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

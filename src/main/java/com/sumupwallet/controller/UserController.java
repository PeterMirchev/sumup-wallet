package com.sumupwallet.controller;

import com.sumupwallet.dto.UserResponseDto;
import com.sumupwallet.mapper.UserMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.response.ApiResponse;
import com.sumupwallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreateUserRequest request) {

        User user = userService.createUser(request);

        UserResponseDto response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User created successfully: ", response));
    }
}

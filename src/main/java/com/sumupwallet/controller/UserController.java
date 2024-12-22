package com.sumupwallet.controller;

import com.sumupwallet.model.dto.UserResponse;
import com.sumupwallet.utils.mapper.UserMapper;
import com.sumupwallet.model.User;
import com.sumupwallet.request.CreateUserRequest;
import com.sumupwallet.request.UpdateUserRequest;
import com.sumupwallet.response.ApiResponse;
import com.sumupwallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User created successfully: ", response));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest request, @PathVariable UUID userId) {

        User user = userService.updateUser(request, userId);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User updated successfully: ", response));
    }

    @GetMapping("/by-id/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable UUID userId) {

        User user = userService.getUser(userId);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam(name = "email") String email) {

        User user = userService.getUserByEmail(email);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable UUID userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(new ApiResponse("User deleted successfully: ", null));
    }
}

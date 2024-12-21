package com.sumupwallet.controller;

import com.sumupwallet.dto.UserResponseDto;
import com.sumupwallet.mapper.UserMapper;
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
        UserResponseDto response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User created successfully: ", response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest request, @PathVariable UUID id) {

        User user = userService.updateUser(request, id);
        UserResponseDto response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User updated successfully: ", response));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable UUID id) {

        User user = userService.getUser(id);
        UserResponseDto response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {

        User user = userService.getUserByEmail(email);
        UserResponseDto response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(new ApiResponse("User deleted successfully: ", null));
    }
}

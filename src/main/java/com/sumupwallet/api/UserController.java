package com.sumupwallet.api;

import com.sumupwallet.model.dto.UserResponse;
import com.sumupwallet.request.GetUserByEmailRequest;
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

import static com.sumupwallet.utils.CommonMessages.*;
import static com.sumupwallet.utils.mapper.Endpoints.USERS_CONTROLLER;

@RestController
@RequestMapping(USERS_CONTROLLER)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    /**
     * Creates a new user.
     * Endpoint: POST http://localhost:8080/api/v1/users
     * @param request The user creation details (firstName, lastName, password, email).
     * @return ResponseEntity with the created user details.
     */
    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreateUserRequest request) {

        User user = userService.createUser(request);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse(USER_SUCCESSFULLY_CREATED, response));
    }

    /**
     * Updates an existing user's information.
     * Endpoint: PUT http://localhost:8080/api/v1/users
     * @param request The user update details (userId, firstName, lastName).
     * @return ResponseEntity with the updated user details.
     */
    @PutMapping()
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest request) {

        User user = userService.updateUser(request);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse(USER_SUCCESSFULLY_UPDATED, response));
    }

    /**
     * Retrieves a user by their ID.
     * Endpoint: GET http://localhost:8080/api/v1/users/{userId}
     * @param userId The ID of the user.
     * @return ResponseEntity with the user details.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable UUID userId) {

        User user = userService.getUser(userId);
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    /**
     * Retrieves a user by their email.
     * Endpoint: GET http://localhost:8080/api/v1/users/email
     * @param request The request containing the email.
     * @return ResponseEntity with the user details.
     */
    @GetMapping("/email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestBody GetUserByEmailRequest request) {

        User user = userService.getUserByEmail(request.getEmail());
        UserResponse response = UserMapper.mapToUserResponseDto(user);

        return ResponseEntity.ok(new ApiResponse("User: ", response));
    }

    /**
     * Deletes a user by their ID.
     * Endpoint: DELETE http://localhost:8080/api/v1/users/{userId}
     * @param userId The ID of the user to delete.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable UUID userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(new ApiResponse(USER_SUCCESSFULLY_DELETED, null));
    }
}

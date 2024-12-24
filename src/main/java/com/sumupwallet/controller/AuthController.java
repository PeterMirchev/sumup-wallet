package com.sumupwallet.controller;

import com.sumupwallet.request.LoginRequest;
import com.sumupwallet.response.ApiResponse;
import com.sumupwallet.response.JwtResponse;
import com.sumupwallet.security.jwt.JwtUtils;
import com.sumupwallet.security.user.WalletUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sumupwallet.utils.CommonMessages.LOGIN_SUCCESS;
import static com.sumupwallet.utils.mapper.Endpoints.AUTH_CONTROLLER;

@RestController
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateTokenForUser(authentication);
        WalletUserDetails userDetails = (WalletUserDetails) authentication.getPrincipal();

        JwtResponse response = new JwtResponse(userDetails.getId(), jwt);

        return ResponseEntity.ok(new ApiResponse(LOGIN_SUCCESS, response));
    }
}

package com.sumupwallet.security.user;

import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.model.User;
import com.sumupwallet.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WalletUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public WalletUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found! Invalid Email - %s".formatted(email)));

        return WalletUserDetails.buildUserDetails(user);
    }

}

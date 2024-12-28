package com.sumupwallet.security.user;

import com.sumupwallet.exception.ResourceNotFoundException;
import com.sumupwallet.model.User;
import com.sumupwallet.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.sumupwallet.utils.ExceptionMessages.USER_WITH_EMAIL_NOT_FOUND;

@Service
public class WalletUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public WalletUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_WITH_EMAIL_NOT_FOUND.formatted(email)));

        return WalletUserDetails.buildUserDetails(user);
    }

}

package com.sumupwallet.security.user;

import com.sumupwallet.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletUserDetails implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public static WalletUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .collect(Collectors.toUnmodifiableList());

        return new WalletUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.bookstorevn.config;

import com.bookstorevn.models.ApplicationUser;
import com.bookstorevn.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String role = user.getRole() != null ? user.getRole() : "CUSTOMER";
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase();
        }

        boolean accountNonLocked = !user.isLocked();

        return new User(
                user.getEmail(),
                user.getPassword(), // This should be hashed in DB
                true,
                true,
                true,
                accountNonLocked,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}

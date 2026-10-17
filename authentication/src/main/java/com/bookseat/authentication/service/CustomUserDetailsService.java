package com.bookseat.authentication.service;

import com.bookseat.authentication.config.UserPrincipal;
import com.bookseat.authentication.dto.AuthRequest;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.repository.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user  = userDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserPrincipal(user);
    }



    //save user details
    public Users saveUserDetails(AuthRequest authRequest) {
        Users user = new Users();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        log.info("Saving user details: {}", user);
        return userDetailsRepository.save(user);
    }

    public Users fetchUserByName(String username) {
        log.info("Fetching user by name: {}", username);
        return userDetailsRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

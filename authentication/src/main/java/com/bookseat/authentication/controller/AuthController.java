package com.bookseat.authentication.controller;

import com.bookseat.authentication.dto.AuthRequest;
import com.bookseat.authentication.entity.Users;
import com.bookseat.authentication.service.CustomUserDetailsService;
import com.bookseat.authentication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//authenticates the user and generates the token
    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("Authenticating User");
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        log.info("Authentication Successful and setting Security Context");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("User Authenticated");
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        log.info("User Details Loaded");
        log.info("Generating JWT Token");
        final String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
        log.info("JWT Token Generated  {}", jwtToken);
        return jwtToken;
    }


    @PostMapping("/register")
    public Users register(@RequestBody AuthRequest authRequest) {
        log.info("Registering User");
        return customUserDetailsService.saveUserDetails(authRequest);

    }

    @GetMapping("/fetch")
    public Users findUserByName(@RequestParam String username) {
        log.info("Fetching User");
        return customUserDetailsService.fetchUserByName(username);
    }

}

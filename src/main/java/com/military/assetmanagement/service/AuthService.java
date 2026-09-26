package com.military.assetmanagement.service;

import com.military.assetmanagement.dto.*;
import com.military.assetmanagement.repository.UserRepository;
import com.military.assetmanagement.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        var user = userRepository.findByUsername(request.username()).orElseThrow();
        UserDetails details = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()).password(user.getPassword())
                .roles(user.getRole().name()).build();

        return new LoginResponse(jwtService.generateToken(details), user.getUsername(),
                user.getRole().name(), user.getBase() == null ? null : user.getBase().getId());
    }
}

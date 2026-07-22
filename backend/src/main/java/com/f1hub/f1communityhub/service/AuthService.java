package com.f1hub.f1communityhub.service;

import com.f1hub.f1communityhub.dto.AuthResponse;
import com.f1hub.f1communityhub.dto.LoginRequest;
import com.f1hub.f1communityhub.dto.RegisterRequest;
import com.f1hub.f1communityhub.entity.User;
import com.f1hub.f1communityhub.mapper.UserMapper;
import com.f1hub.f1communityhub.repository.UserRepository;
import com.f1hub.f1communityhub.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Ce username est déjà pris");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }

        User user = userMapper.toEntity(request, passwordEncoder);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return userMapper.toAuthResponse(user, token);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Username ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getUsername());

        return userMapper.toAuthResponse(user, token);
    }
}
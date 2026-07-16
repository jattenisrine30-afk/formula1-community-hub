package com.f1hub.f1communityhub.mapper;

import com.f1hub.f1communityhub.dto.AuthResponse;
import com.f1hub.f1communityhub.dto.RegisterRequest;
import com.f1hub.f1communityhub.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    public AuthResponse toAuthResponse(User user, String token) {
        return new AuthResponse(token, user.getUsername());
    }
}

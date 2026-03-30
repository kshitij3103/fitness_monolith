package com.example.fitness.service;

import com.example.fitness.dto.LoginRequest;
import com.example.fitness.dto.RegisterRequest;
import com.example.fitness.dto.UserResponse;
import com.example.fitness.model.User;
import com.example.fitness.model.UserRole;
import com.example.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service

@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponse register(RegisterRequest registerRequest) {
        UserRole role = registerRequest.getRole() != null ? registerRequest.getRole() : UserRole.USER;
        User user = User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();
//        User user = new User(
//                null,
//                registerRequest.getEmail(),
//                registerRequest.getPassword(),
//                registerRequest.getFirstName(),
//                registerRequest.getLastName(),
//                Instant
//                .parse("2025-12-08T14:49:41.208Z")
//                .atZone(ZoneOffset.UTC)
//                .toLocalDateTime(),
//                 Instant
//                .parse("2025-12-08T14:49:41.208Z")
//                .atZone(ZoneOffset.UTC)
//                .toLocalDateTime(),
//                List.of(),
//                List.of()
//
//        );
        User savedUser =userRepository.save(user);

        return mapToResponse(savedUser);

    }

    public UserResponse mapToResponse(User savedUser) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse;
    }

    public  User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user==null)
            throw new RuntimeException("Invalid credentials");
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

}

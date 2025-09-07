package com.example.ai_social_assistant.service.impl;

import com.example.ai_social_assistant.dto.request.LoginRequest;
import com.example.ai_social_assistant.dto.request.UserRequest;
import com.example.ai_social_assistant.dto.response.response.AuthenticationResponse;
import com.example.ai_social_assistant.exception.BusinessException;
import com.example.ai_social_assistant.model.entity.user.User;
import com.example.ai_social_assistant.model.entity.user.UserRole;
import com.example.ai_social_assistant.model.enumeration.BusinessExceptionStatus;
import com.example.ai_social_assistant.model.enumeration.RoleEnum;
import com.example.ai_social_assistant.model.enumeration.StatusUserEnum;
import com.example.ai_social_assistant.repository.UserRepository;
import com.example.ai_social_assistant.repository.UserRoleJDBCRepo;
import com.example.ai_social_assistant.repository.UserRoleRepository;
import com.example.ai_social_assistant.security.JwtService;
import com.example.ai_social_assistant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleJDBCRepo userRoleJDBCRepo;

    @Override
    public AuthenticationResponse register(UserRequest request) {
        Optional<User> userOptional = userRepository.findByEmailOrUsername(request.getEmail(), request.getUsername());
        if (userOptional.isPresent()) {
            throw new BusinessException(BusinessExceptionStatus.USER_ALREADY_EXISTS);
        }

        // Tạo User mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setVisibleName(request.getVisibleName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(StatusUserEnum.ACTIVE);

        User userSaved = userRepository.save(user);

        // Tạo role mặc định
        UserRole userRole = new UserRole();
        userRole.setUserId(Math.toIntExact(userSaved.getId()));
        userRole.setRoleId(1); // ROLE_USER
        userRoleRepository.save(userRole);

        user.setRoles(List.of(RoleEnum.ACCOUNT_USER.getRole()));

        // Generate JWT + response
        return response(user);
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BusinessException(BusinessExceptionStatus.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(BusinessExceptionStatus.USER_NOT_FOUND));

        user.setRoles(userRoleJDBCRepo.getRoleNamesByUserId(Math.toIntExact(user.getId())));

        // Generate JWT + response
        return response(user);
    }

    private AuthenticationResponse response(User user) {
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setId(Math.toIntExact(user.getId()));
        authResponse.setUsername(user.getUsername());
        authResponse.setAccessToken(jwtService.generateToken(user));
        authResponse.setEmail(user.getEmail());
        authResponse.setRoles(user.getRoles());
        authResponse.setVisibleName(user.getVisibleName());
        return authResponse;
    }

    @Override
    public String logout(String userName) {
        // Không cần Redis, chỉ trả thông báo
        return "Đăng xuất thành công";
    }
}

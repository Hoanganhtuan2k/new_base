package com.example.thanhan.service.impl;


import com.example.thanhan.common.AppConstants;
import com.example.thanhan.dto.request.LoginRequest;
import com.example.thanhan.dto.request.UserRequest;
import com.example.thanhan.dto.response.response.AuthenticationResponse;
import com.example.thanhan.exception.BusinessException;
import com.example.thanhan.model.entity.user.User;
import com.example.thanhan.model.entity.user.UserRole;
import com.example.thanhan.model.enumeration.BusinessExceptionStatus;
import com.example.thanhan.model.enumeration.RoleEnum;
import com.example.thanhan.model.enumeration.StatusUserEnum;
import com.example.thanhan.repository.UserRepository;
import com.example.thanhan.repository.UserRoleJDBCRepo;
import com.example.thanhan.repository.UserRoleRepository;
import com.example.thanhan.security.JwtService;
import com.example.thanhan.service.AuthenticationService;
import com.example.thanhan.service.RedisCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleJDBCRepo userRoleJDBCRepo;
    private final RedisCommonService redisAdapterService;

    @Override
    public AuthenticationResponse register(UserRequest request) {
        Optional<User> userOptional = userRepository.findByEmailOrUsername(request.getEmail(), request.getUsername());
        if (userOptional.isPresent()) {
            throw new BusinessException(BusinessExceptionStatus.USER_ALREADY_EXISTS);
        }
        // lấy thông tin account từ request
        User user = new User();
        user.setUsername(request.getUsername());
        user.setVisibleName(request.getVisibleName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setStatus(StatusUserEnum.ACTIVE);
        User userSaved = userRepository.save(user);
        //tạo role
        UserRole userRole = new UserRole();
        userRole.setUserId(userSaved.getId());
        userRole.setRoleId(1);
        userRoleRepository.save(userRole);
        user.setRoles(List.of(RoleEnum.ACCOUNT_USER.getRole()));
        AuthenticationResponse authenticationResponse = response(user);
        redisAdapterService.set(AppConstants.MY_APP_TOKEN_PREFIX + request.getUsername(),
                authenticationResponse, TimeUnit.HOURS.toSeconds(3));
        return authenticationResponse;
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
        user.setRoles(userRoleJDBCRepo.getRoleNamesByUserId(user.getId()));
        AuthenticationResponse authentic = response(user);
        redisAdapterService.set(AppConstants.MY_APP_TOKEN_PREFIX + request.getUsername(),
                authentic, TimeUnit.HOURS.toSeconds(3));
        return authentic;
    }

    private AuthenticationResponse response(User user) {
        AuthenticationResponse jwtAcs = new AuthenticationResponse();
        jwtAcs.setId(user.getId());
        jwtAcs.setUsername(user.getUsername());
        jwtAcs.setAccessToken(jwtService.generateToken(user));
        jwtAcs.setEmail(user.getEmail());
        jwtAcs.setRoles(user.getRoles());
        jwtAcs.setVisibleName(user.getVisibleName());
        return jwtAcs;
    }

    @Override
    public String logout(String userName) {
        redisAdapterService.delete(AppConstants.MY_APP_TOKEN_PREFIX + userName);
        return "Đăng xuất thành công";
    }

}

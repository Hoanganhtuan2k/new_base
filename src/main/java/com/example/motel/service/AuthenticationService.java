package com.example.motel.service;

import com.example.motel.dto.request.LoginRequest;
import com.example.motel.dto.request.UserRequest;
import com.example.motel.dto.response.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(UserRequest request);

    AuthenticationResponse authenticate(LoginRequest request);

    String logout(String userName);
}

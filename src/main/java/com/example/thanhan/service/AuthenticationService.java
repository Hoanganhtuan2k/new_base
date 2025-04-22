package com.example.thanhan.service;

import com.example.thanhan.dto.request.LoginRequest;
import com.example.thanhan.dto.request.UserRequest;
import com.example.thanhan.dto.response.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(UserRequest request);

    AuthenticationResponse authenticate(LoginRequest request);

    String logout(String userName);
}

package com.example.ai_social_assistant.service;

import com.example.ai_social_assistant.dto.request.LoginRequest;
import com.example.ai_social_assistant.dto.request.UserRequest;
import com.example.ai_social_assistant.dto.response.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(UserRequest request);

    AuthenticationResponse authenticate(LoginRequest request);

    String logout(String userName);
}

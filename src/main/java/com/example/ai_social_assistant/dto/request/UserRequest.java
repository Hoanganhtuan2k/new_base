package com.example.ai_social_assistant.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String visibleName;
}

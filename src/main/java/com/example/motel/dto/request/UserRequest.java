package com.example.motel.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String visibleName;
}

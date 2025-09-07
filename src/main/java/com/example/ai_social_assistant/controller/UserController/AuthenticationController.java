package com.example.ai_social_assistant.controller.UserController;


import com.example.ai_social_assistant.common.BaseResponse;
import com.example.ai_social_assistant.dto.request.LoginRequest;
import com.example.ai_social_assistant.dto.request.UserRequest;
import com.example.ai_social_assistant.dto.response.response.AuthenticationResponse;
import com.example.ai_social_assistant.model.BaseController;
import com.example.ai_social_assistant.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthenticationController extends BaseController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public BaseResponse<AuthenticationResponse> register(
            @RequestBody UserRequest request) {
        return BaseResponse.success(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public BaseResponse<AuthenticationResponse> login(
            @RequestBody LoginRequest request)  {
        return BaseResponse.success(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout() {
        return BaseResponse.success(authenticationService.logout(this.getUsername()));
    }

}

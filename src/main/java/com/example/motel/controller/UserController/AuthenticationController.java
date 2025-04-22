package com.example.motel.controller.UserController;


import com.example.motel.common.BaseResponse;
import com.example.motel.dto.request.LoginRequest;
import com.example.motel.dto.request.UserRequest;
import com.example.motel.dto.response.response.AuthenticationResponse;
import com.example.motel.model.BaseController;
import com.example.motel.service.AuthenticationService;
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

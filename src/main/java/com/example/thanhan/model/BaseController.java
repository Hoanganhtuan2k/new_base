package com.example.thanhan.model;


import com.example.thanhan.common.AppConstants;
import com.example.thanhan.dto.response.response.AuthenticationResponse;
import com.example.thanhan.security.JwtService;
import com.example.thanhan.service.RedisCommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisCommonService redisService;
    @Autowired
    private JwtService jwtService;

    protected String getUsername() {
        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
            return jwtService.extractUsername(token.substring(AppConstants.HEADER_TOKEN_PREFIX.length()));
        }
        return "";
    }

    protected AuthenticationResponse getUserInfo() {
        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
            String key = AppConstants.MY_APP_TOKEN_PREFIX + jwtService.extractUsername(accessToken);
            return this.redisService.get(key, AuthenticationResponse.class);
        }
        return null;
    }
//
//    protected List<String> getGroups() {
//        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
//        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
//            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
//            String key = SecurityUltil.SHOP_AppConstants.HEADER_TOKEN_PREFIX + accessToken.hashCode();
//            GetUserInfoResponse response = this.redisService.get(key, GetUserInfoResponse.class);
//            return response.getGroups();
//        }
//        return Collections.emptyList();
//    }
//
//    protected List<String> getRoles() {
//        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
//        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
//            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
//            String key = SecurityUltil.SHOP_AppConstants.HEADER_TOKEN_PREFIX + accessToken.hashCode();
//            GetUserInfoResponse response = this.redisService.get(key, GetUserInfoResponse.class);
//            return response.getRoles();
//        }
//        return Collections.emptyList();
//    }
//
//    protected List<String> getRights() {
//        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
//        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
//            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
//            String key = SecurityUltil.SHOP_AppConstants.HEADER_TOKEN_PREFIX + accessToken.hashCode();
//            GetUserInfoResponse response = this.redisService.get(key, GetUserInfoResponse.class);
//            return response.getRights();
//        }
//        return Collections.emptyList();
//    }
//
//    protected String getUserT24() {
//        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
//        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
//            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
//            String key = SecurityUltil.SHOP_AppConstants.HEADER_TOKEN_PREFIX + accessToken.hashCode();
//            GetUserInfoResponse response = this.redisService.get(key, GetUserInfoResponse.class);
//            return response.getUserT24();
//        }
//        return null;
//    }

    protected String getToken() {
        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
            return token;
        }
        return null;
    }
//
//    protected String getSiteCode() {
//        String token = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
//        if (token != null && token.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
//            String accessToken = token.substring(AppConstants.HEADER_TOKEN_PREFIX.length());
//            String key = SecurityUltil.SHOP_AppConstants.HEADER_TOKEN_PREFIX + accessToken.hashCode();
//            GetUserInfoResponse response = this.redisService.get(key, GetUserInfoResponse.class);
//            return response.getSiteCode();
//        }
//        return "";
//    }

}

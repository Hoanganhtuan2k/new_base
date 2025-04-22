package com.example.thanhan.security;

import com.example.thanhan.common.AppConstants;
import com.example.thanhan.dto.response.ErrorResponse;
import com.example.thanhan.dto.response.response.AuthenticationResponse;
import com.example.thanhan.model.entity.user.User;
import com.example.thanhan.model.enumeration.BusinessExceptionStatus;
import com.example.thanhan.service.RedisCommonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final RedisCommonService redisAdapterService;
    private final MessageSource messageSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AppConstants.HEADER_AUTHORIZATION);
        final String jwt;
        final String userEmail;
        if (authorizationHeader == null || !authorizationHeader.startsWith(AppConstants.HEADER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authorizationHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AuthenticationResponse authenticationResponse = redisAdapterService.get(AppConstants.MY_APP_TOKEN_PREFIX + userEmail, AuthenticationResponse.class);
                if (authenticationResponse == null) {
                    handleException(response, BusinessExceptionStatus.EXPIRED_TOKEN);
                    return;
                }
                User user = new User();
                user.setUsername(authenticationResponse.getUsername());
                user.setRoles(authenticationResponse.getRoles());
                UserDetails userDetails = user;
                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    handleException(response, BusinessExceptionStatus.AUTHENTICATION_FAILED);
                    return;
                }
            } else {
                handleException(response, BusinessExceptionStatus.AUTHENTICATION_FAILED);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleException(response, BusinessExceptionStatus.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            handleException(response, BusinessExceptionStatus.INVALID_SIGNATURE);
        } catch (Exception e) {
            e.printStackTrace();
            handleException(response, BusinessExceptionStatus.AUTHENTICATION_FAILED);
        }
    }

    private void handleException(HttpServletResponse response, BusinessExceptionStatus status) throws IOException {
        String localizedMessage = messageSource.getMessage(status.getMessage(), null, status.getMessage(), null);
        response.setStatus(status.getCode());
        response.setContentType("application/json");
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        ErrorResponse errorResponse = ErrorResponse.builder().status(status.getCode()).code(status.getMessage()).message(localizedMessage).build();
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter().flush();
    }
}
package com.example.motel.security;

import com.example.motel.common.AppConstants;
import com.example.motel.dto.response.response.AuthenticationResponse;
import com.example.motel.service.RedisCommonService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private static final long JWT_TOKEN_VALIDITY = 15 * 24 * 60 * 60;
    private final KeyPair keyPair;
    private final RedisCommonService redisAdapterService;

    public JwtService(RedisCommonService redisAdapterService) throws NoSuchAlgorithmException {
        this.redisAdapterService = redisAdapterService;
        this.keyPair = generateKeyPair();
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(4096);
            return generator.generateKeyPair();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(keyPair.getPrivate())
                .claim("scope",scopeBuilder(userDetails.getAuthorities()))
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        AuthenticationResponse authenticationResponse = redisAdapterService.get(AppConstants.MY_APP_TOKEN_PREFIX + username, AuthenticationResponse.class);
        if (authenticationResponse == null || !authenticationResponse.getAccessToken().equals(token)) {
            return false;
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String scopeBuilder(Collection<? extends GrantedAuthority> list) {
        StringJoiner joiner = new StringJoiner(" ");
        for (GrantedAuthority item : list) {
            joiner.add(item.getAuthority());
        }
        return joiner.toString();
    }

}

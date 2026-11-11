package com.example.ai_social_assistant.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private static final long JWT_TOKEN_VALIDITY = 15 * 24 * 60 * 60 * 1000L; // 15 ngày, đơn vị ms
    private final KeyPair keyPair;

    public JwtService(@Value("${jwt.private-key:}") String privateKeyPem,
                      @Value("${jwt.public-key:}") String publicKeyPem) throws GeneralSecurityException {
        this.keyPair = resolveKeyPair(privateKeyPem, publicKeyPem);
    }

    private static KeyPair resolveKeyPair(String privateKeyPem, String publicKeyPem) throws GeneralSecurityException {
        boolean hasPrivate = privateKeyPem != null && !privateKeyPem.isBlank();
        boolean hasPublic = publicKeyPem != null && !publicKeyPem.isBlank();
        if (hasPrivate && hasPublic) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodePem(privateKeyPem)));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodePem(publicKeyPem)));
            return new KeyPair(publicKey, privateKey);
        }
        if (hasPrivate || hasPublic) {
            throw new IllegalStateException("Both jwt.private-key and jwt.public-key must be configured");
        }
        log.warn("jwt.private-key/jwt.public-key not configured: using an ephemeral RSA key pair, tokens will be invalidated on restart");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(4096);
        return generator.generateKeyPair();
    }

    private static byte[] decodePem(String pem) {
        String body = pem.replaceAll("-----(BEGIN|END) [A-Z ]+-----", "").replaceAll("\\s", "");
        return Base64.getDecoder().decode(body);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object scope = claims.get("scope");
        if (scope != null) {
            return Arrays.asList(scope.toString().split(" "));
        }
        return Collections.emptyList();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(keyPair.getPrivate())
                .claim("scope", scopeBuilder(userDetails.getAuthorities()))
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(keyPair.getPublic()) // public key để verify
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("JWT token expired");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    private static String scopeBuilder(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}

package com.example.ai_social_assistant.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final UserDetails USER = User.withUsername("alice").password("x").authorities("ROLE_USER").build();

    private static KeyPair newKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    private static String pem(String type, byte[] der) {
        return "-----BEGIN " + type + "-----\n" + Base64.getMimeEncoder().encodeToString(der) + "\n-----END " + type + "-----";
    }

    @Test
    void configuredKeysSurviveRestart() throws Exception {
        KeyPair kp = newKeyPair();
        String priv = pem("PRIVATE KEY", kp.getPrivate().getEncoded());
        String pub = pem("PUBLIC KEY", kp.getPublic().getEncoded());

        String token = new JwtService(priv, pub).generateToken(USER);
        JwtService restarted = new JwtService(priv, pub);

        assertEquals("alice", restarted.extractUsername(token));
        assertTrue(restarted.validateToken(token, USER));
        assertEquals("ROLE_USER", restarted.extractRoles(token).get(0));
    }

    @Test
    void singleLineBase64KeysAreAccepted() throws Exception {
        KeyPair kp = newKeyPair();
        String priv = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());
        String pub = Base64.getEncoder().encodeToString(kp.getPublic().getEncoded());

        JwtService service = new JwtService(priv, pub);

        assertEquals("alice", service.extractUsername(service.generateToken(USER)));
    }

    @Test
    void ephemeralKeysDoNotSurviveRestart() throws Exception {
        String token = new JwtService("", "").generateToken(USER);

        assertThrows(RuntimeException.class, () -> new JwtService("", "").extractUsername(token));
    }

    @Test
    void partialConfigurationIsRejected() throws Exception {
        String pub = Base64.getEncoder().encodeToString(newKeyPair().getPublic().getEncoded());

        assertThrows(IllegalStateException.class, () -> new JwtService("", pub));
    }
}

package com.f1hub.f1communityhub.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService("cle-de-test-uniquement-assez-longue-pour-hmac-256-bits", 86400000L);
    }

    @Test
    void generateToken_devraitCreerUnTokenNonVide() {
        String token = jwtService.generateToken("ahmed123");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_devraitRetrouverLeBonUsername() {
        String token = jwtService.generateToken("ahmed123");

        String username = jwtService.extractUsername(token);

        assertEquals("ahmed123", username);
    }

    @Test
    void isTokenValid_devraitEtreVraiPourLeBonUsername() {
        String token = jwtService.generateToken("ahmed123");

        boolean valid = jwtService.isTokenValid(token, "ahmed123");

        assertTrue(valid);
    }

    @Test
    void isTokenValid_devraitEtreFauxPourUnAutreUsername() {
        String token = jwtService.generateToken("ahmed123");

        boolean valid = jwtService.isTokenValid(token, "nisrine");

        assertFalse(valid);
    }
}
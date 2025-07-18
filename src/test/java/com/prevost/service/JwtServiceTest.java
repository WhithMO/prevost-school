package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.prevost.model.Rol;
import com.prevost.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();
    // must match the SECRET_KEY in JwtService
    private static final String SECRET_KEY = "wN7pQzA2xFhT8mJbYgRVkCs9DdLWEF9489234C499C4W4C4C5f43EF9ecew4";

    @Test
    void generateToken_createsValidJwtContainingUsernameAndRole() {
        // Arrange
        Usuario user = new Usuario();
        user.setUsername("john");
        Rol role = new Rol();
        role.setNombre("ADMIN");
        user.setRol(role);

        // Act
        String token = jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
        Claims claims = jwtService.extractClaims(token);
        assertEquals("john", claims.getSubject());
        assertEquals("ROLE_ADMIN", claims.get("rol", String.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
    }

    @Test
    void extractUsername_and_extractRole_returnCorrectValues() {
        // Arrange
        Usuario user = new Usuario();
        user.setUsername("alice");
        Rol role = new Rol();
        role.setNombre("USER");
        user.setRol(role);
        String token = jwtService.generateToken(user);

        // Act & Assert
        assertEquals("alice", jwtService.extractUsername(token));
        assertEquals("ROLE_USER", jwtService.extractRole(token));
    }

    @Test
    void isTokenValid_returnsTrueForFreshToken() {
        // Arrange
        Usuario user = new Usuario();
        user.setUsername("bob");
        Rol role = new Rol();
        role.setNombre("GUEST");
        user.setRol(role);
        String token = jwtService.generateToken(user);

        // Act
        boolean valid = jwtService.isTokenValid(token);

        // Assert
        assertTrue(valid);
    }

    @Test
    void isTokenValid_returnsFalseForExpiredToken() {
        // Arrange: build a token that expired 1 second ago
        Date now = new Date();
        Date issuedAt = new Date(now.getTime() - 2000);
        Date expiration = new Date(now.getTime() - 1000);
        String expiredToken = Jwts.builder()
                .setSubject("charlie")
                .claim("rol", "ROLE_USER")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // Act
        boolean valid = jwtService.isTokenValid(expiredToken);

        // Assert
        assertFalse(valid);
    }

    @Test
    void isTokenValid_returnsFalseForInvalidToken() {
        // Arrange
        String invalidToken = "this.is.not.a.valid.jwt";

        // Act
        boolean valid = jwtService.isTokenValid(invalidToken);

        // Assert
        assertFalse(valid);
    }
}

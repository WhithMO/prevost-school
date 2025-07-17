package com.prevost.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.prevost.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private static final String SECRET_KEY = "wN7pQzA2xFhT8mJbYgRVkCs9DdLWEF9489234C499C4W4C4C5f43EF9ecew4";

	public String generateToken(Usuario usuario) {
	    return Jwts.builder()
	        .setSubject(usuario.getUsername())
	        .claim("rol", "ROLE_" + usuario.getRol().getNombre())
	        .setIssuedAt(new Date())
	        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 1 día
	        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	        .compact();
	}

    public Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("rol", String.class); // 👈 Extraer rol desde el token
    }

    public boolean isTokenValid(String token) {
        try {
            return extractClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

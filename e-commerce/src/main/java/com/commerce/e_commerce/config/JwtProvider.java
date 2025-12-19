package com.commerce.e_commerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {
    SecretKey secretKey = Keys.hmacShaKeyFor(JWT_CONST.SECRET_KEY.getBytes());
    public String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+864000000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromJwtToken(String token){
        token = token.substring(7);
        Claims claims = Jwts.parser() // Use Jwts.parser() which returns a builder in modern versions
                .verifyWith(secretKey) // Replaces .setSigningKey(secretKey)
                .build()
                .parseSignedClaims(token) // Replaces .parseClaimsJws(token)
                .getPayload(); // Replaces .getBody()
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",",authoritiesSet);
    }
}

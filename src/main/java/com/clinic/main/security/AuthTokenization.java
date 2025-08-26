package com.clinic.main.security;

import com.clinic.main.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthTokenization {

    private String jwtSecurityKey = "acbwiwnijs83r8fw0dfynef9wrn8egewwheeufghe3f3rf83r70386461371456254r67ny87eh6c7e6fg3fmnw9xwufbh3e7f33f3e9wf";

    private SecretKey getSecreteKey() {
        return Keys.hmacShaKeyFor(jwtSecurityKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        // Create payload info.
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                .signWith(getSecreteKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims payload = Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return payload.getSubject();
    }
}

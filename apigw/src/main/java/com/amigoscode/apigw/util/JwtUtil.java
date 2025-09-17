package com.amigoscode.apigw.util;

import com.amigoscode.apigw.exception.jwt.JwtTokenMalformedException;
import com.amigoscode.apigw.exception.jwt.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    public Claims getClaims(String token) {
        try {
            System.out.println("token in claims " + token);
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String validateToken(String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            System.out.println("from rq : " + token);
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("token kosong");
            }
            if (!token.startsWith("Bearer ")) {
                throw new RuntimeException("needs bearer");
            }
            token = token.substring(7);
            System.out.println("subs " + token);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return token;
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

}
package com.amigoscode.common.util.jwt;

import com.amigoscode.common.exception.jwt.*;
import com.amigoscode.common.request.GenerateTokenRequest;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.jwtExpirationMs}")
    private Integer expiration;

    private final String bearer = "Bearer ";

    public String generateToken(GenerateTokenRequest request) {
        Claims claims = Jwts
                .claims()
                .setIssuer(request.getId().toString())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expiration));
        claims.put("username", request.getUsername());
        claims.put("email", request.getEmail());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public void verify(String authorization) {
        try {
            if (authorization == null || authorization.isEmpty()) {
                throw new UnauthorizedException();
            }
            if (authorization.startsWith(bearer)) {
                authorization = authorization.substring(bearer.length());
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authorization);
        } catch (SignatureException e) {
            throw new JwtSignatureException(e.getMessage());
        } catch (MalformedJwtException e) {
            throw new JwtMalformedException(e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException(e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JwtClaimsException(e.getMessage());
        }
    }
}

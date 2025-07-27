package com.security.userservice.service;

import com.security.userservice.model.User;
import io.jsonwebtoken.Jwts;
//mport org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//import static sun.net.www.protocol.http.AuthenticatorKeys.getKey;

@Service
public class JWTService {

    private UserDetailsService userDetailsService;
    private String secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; //1 hour
    JWTService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(User user) {
        userDetailsService.loadUserByUsername(user.getUsername());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }
     private SecretKey getKey() {
         byte[] keyBytes = Decoders.BASE64.decode(secretKey);
         return Keys.hmacShaKeyFor(keyBytes);
     }
//      }  private SecretKey generateKey() {
//        return Keys.hmacShaKeyFor(getSecretKey().getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String getSecretKey(){
//        secretKey = "c6ffd2c47680c9b4d8a609e7619c3bfcd2718f37f99074fa70bb0c6400943d91";
//        return secretKey;
//    }

    public String extractUsername(String token) {
         return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            return extractUsername(token).equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}

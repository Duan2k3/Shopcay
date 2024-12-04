package com.example.shop.components;

import com.example.shop.exceptions.InvalidParamException;
import com.example.shop.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generteToken(User user) throws Exception{
        Map<String,Object> claims = new HashMap<>();
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getAccountLogin())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch (Exception e){
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
        }

    }
    private Key getSignKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new  byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    private Claims extracAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //parseClaimsJws(token)

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extracAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public  boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractAccount(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token , UserDetails userDetails){
        String account = extractAccount(token);
        return (account.equals(userDetails.getUsername()))
                && !isTokenExpired(token);

    }
}

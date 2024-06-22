package com.bottle_app.util;

import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    @Autowired
    private UserDetailsService userDetailsService;

    //get secret key using for encoding,decoding
    private SecretKey getSigningKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //create jwt tokens
    public TokenDto generateToken(User user){
        String access_token = this.generateAccessToken(user);
        String refresh_token = this.generateRefreshToken(user);

        return TokenDto.builder()
                .access_token(access_token)
                .refresh_token(refresh_token)
                .expires_in(Long.valueOf(accessTokenExpiration/1000).intValue())
                .scope("")
                .token_type("Bearer")
                .last_bottle_creation(user.getLastBottleCreation())
                .build();
    }

    //get access token from header
    public String resolveAccessToken(HttpServletRequest request){
        if(request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer ")
                    && request.getHeader("Authorization").length() > 7){
            return request.getHeader("Authorization").substring(7);
        }
        return null;
    }

    //get refresh token from header
    public String resolveRefreshToken(HttpServletRequest request){
        if(request.getHeader("RefreshToken") != null && request.getHeader("RefreshToken").startsWith("Bearer ")
                    && request.getHeader("RefreshToken").length() > 7){
            return request.getHeader("RefreshToken").substring(7);
        }
        return null;
    }

    //get authentication from access token
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //get userEmail from token
    public String getUserEmail(String token){
        return Jwts.parser().verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    //recreate access token
    public String regenerateAccessToken(String username){
        //TO DO
        return "";
    }


    //create access token
    private String generateAccessToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();

    }

    //create refresh token
    private String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    //token validation check
    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().verifyWith(this.getSigningKey()).build()
                    .parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}

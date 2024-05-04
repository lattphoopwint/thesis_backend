package com.mylmsapp.springbootlibrary.config;

import com.mylmsapp.springbootlibrary.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "b1a0238b1ffc310b2fad7afb9d615d3306dc6fd91e80506dcaa0009a4545a0ac";
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");
    }
    //generate a token without any claims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //generate a token out of extraclaims and the user details
    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ){
        User user = (User) userDetails; // Assuming UserDetails is an instance of User
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("role", user.getRole().toString()); // Include role as a claim
        claims.put("id", user.getId().toString());

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //implement a method that will validate a token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        // Extract role from the token
        final String roleFromToken = extractRoleFromToken(token);
        User user = (User) userDetails;
        //first let's check whether the entered token is coming from the valid/same user
        //secondly, we will check token's expiration
        return (username.equals(userDetails.getUsername())) && (roleFromToken.equals(user.getRole().toString())) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver ){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // Retrieve the claims (payload)
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

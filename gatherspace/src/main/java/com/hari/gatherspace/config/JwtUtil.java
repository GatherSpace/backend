package com.hari.gatherspace.config;

 import io.jsonwebtoken.Claims;
 import io.jsonwebtoken.Jwts;
 import io.jsonwebtoken.SignatureAlgorithm;
 import io.jsonwebtoken.io.Decoders;
 import io.jsonwebtoken.security.Keys;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.stereotype.Component;

 import java.security.Key;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.function.Function;

 @Component
 public class JwtUtil {

     @Value("${jwt.secret}")
     private String SECRET_KEY = "404E635266556A586A3272357538782F413F4428472B4B6250655368566D5970";

     public String generateToken(String username) {
         return generateToken(new HashMap<>(), username);
     }
     public String generateToken(Map<String, String> extraClaims, String username) {
         System.out.println(SECRET_KEY);
          return Jwts.builder()
                 .setClaims(extraClaims)
                 .setSubject(username)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                 .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                 .compact();
     }

     public String extractToken(HttpServletRequest request) {
         final String bearerToken = request.getHeader("Authorization");
         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
             return bearerToken.substring(7);
         }
         return null;
     }


     private Key getSigningKey(){
         byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
         return Keys.hmacShaKeyFor(keyBytes);
     }

     public String extractUsername(String token) {
         return extractClaim(token, Claims::getSubject);
     }

     public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
         final Claims claims = extractAllClaims(token);
         return claimResolver.apply(claims);
     }

     private Claims extractAllClaims(String token) {
         return Jwts
                 .parser()
                 .setSigningKey(getSigningKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
     }

     public boolean isTokenValid(String token, UserDetails userDetails) {
         final String username = extractUsername(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
     }

     private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
     }
 }
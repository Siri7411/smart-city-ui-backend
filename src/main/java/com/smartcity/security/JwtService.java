package com.smartcity.security;

import com.smartcity.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;
  private final String issuer;
  private final long accessTokenMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.accessTokenMinutes}") long accessTokenMinutes) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.issuer = issuer;
    this.accessTokenMinutes = accessTokenMinutes;
  }

  public String generateToken(AppUser user) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(user.getEmail())
        .issuer(issuer)
        .claim("role", user.getRole().name())
        .claim("name", user.getName())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(accessTokenMinutes * 60)))
        .signWith(key)
        .compact();
  }

  public Jws<Claims> parseToken(String token) {
    return Jwts.parser().verifyWith(key).requireIssuer(issuer).build().parseSignedClaims(token);
  }
}


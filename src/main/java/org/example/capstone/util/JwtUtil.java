package org.example.capstone.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  @PostConstruct
  public void init() {
    System.out.println("Loaded JWT secret: " + secretKey);
  }

  public Long extractUserId(String token) {
    try {
      JwtParser parser = Jwts.parser()
              .verifyWith(secretKey)
              .build();

      Claims claims = parser
              .parseSignedClaims(token.replace("Bearer ", ""))
              .getPayload();

      logger.info("JWT 토큰 원본: {}", token);
      logger.info("JWT claim : {}", claims);
      Object idClaim = claims.get("userId");
      if (idClaim == null) {
        throw new IllegalArgumentException("JWT 토큰에 id 클레임이 없습니다.");
      }
      return Long.valueOf(idClaim.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다: " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }
  }
}
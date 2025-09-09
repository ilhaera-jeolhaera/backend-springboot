package org.example.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 20, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 60, nullable = false)
  private String password;

  @Column(name = "email", length = 50, nullable = false, unique = true)
  private String email;

  @Column(name = "refresh_token", length = 255)
  private String refreshToken;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}

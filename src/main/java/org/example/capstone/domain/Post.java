package org.example.capstone.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(name = "user_name")
  private String username;

  @Lob
  @Column(nullable = false)
  private String content;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostImage> images = new ArrayList<>();

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private int likes;
}

package org.example.capstone.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Comment parent;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @Lob
  private String content;

  @Column(name = "user_name")
  private String username;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private int likes;
}
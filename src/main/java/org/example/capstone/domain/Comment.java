package org.example.capstone.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
public class Comment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  private Post post;

  @ManyToOne
  @Column(name = "parent_id")
  private Comment parent;

  @ManyToOne
  @Column(name = "post_id")
  private Post postId;

  @Lob
  private String content;

  @Column(name = "user_name")
  private String username;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private int like;
}
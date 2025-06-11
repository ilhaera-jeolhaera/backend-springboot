package org.example.capstone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "like_posts")
public class LikePost {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @ManyToOne
  @JoinColumn(name = "posts_id")
  private Post post;

  public LikePost(String username, Post post) {
    this.username = username;
    this.post = post;
  }
}

package org.example.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@Table(name = "post_likes")
public class LikePost {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  public LikePost(String username, Post post) {
    this.username = username;
    this.post = post;
  }
}

package org.example.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Comment parentId;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post postId;

  @Lob
  private String content;

  @Column(name = "user_name")
  private String username;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
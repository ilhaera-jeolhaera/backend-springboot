package org.example.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_images")
public class PostImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column (name = "file_fetch")
  private String imageUrl;

  @ManyToOne
  @JoinColumn (name = "post_id")
  private Post post;
}

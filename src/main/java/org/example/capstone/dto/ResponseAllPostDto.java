package org.example.capstone.dto;

import lombok.Getter;
import org.example.capstone.entity.Category;

import java.time.LocalDateTime;

@Getter
public class ResponseAllPostDto {
  private Long id;
  private String title;
  private Category category;
  private String content;
  private String username;
  private Long like;
  private LocalDateTime createdAt;

  public ResponseAllPostDto(Long id, String title, Category category,
                            String content, String username, Long like,
                            LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.content = content;
    this.username = username;
    this.like = like;
    this.createdAt = createdAt;
  }
}

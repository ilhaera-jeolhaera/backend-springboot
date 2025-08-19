package org.example.capstone.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.capstone.entity.Category;
import org.example.capstone.entity.Post;
import org.example.capstone.entity.PostImage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ResponseAllPostDto {
  private Long id;
  private String title;
  private Category category;
  private String content;
  private String username;
  private int like;
  private LocalDateTime createdAt;

  public static ResponseAllPostDto from(Post post) {
    return ResponseAllPostDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .category(post.getCategory())
            .content(post.getContent())
            .username(post.getUsername())
            .like(post.getLikes().size())
            .createdAt(post.getCreatedAt())
            .build();
  }
}

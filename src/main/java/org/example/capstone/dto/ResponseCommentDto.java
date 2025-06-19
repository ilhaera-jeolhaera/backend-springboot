package org.example.capstone.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.capstone.entity.Comment;

import java.time.LocalDateTime;

@Getter @Builder
public class ResponseCommentDto {
  private Long id;
  private Long parentId;
  private Long postId;
  private String content;
  private String username;
  private LocalDateTime createdAt;

  public static ResponseCommentDto from(Comment comment) {
    return ResponseCommentDto.builder()
            .id(comment.getId())
            .parentId(comment.getParentId() != null ? comment.getParentId().getId() : null)
            .postId(comment.getPostId().getId())
            .content(comment.getContent())
            .username(comment.getUsername())
            .createdAt(comment.getCreatedAt())
            .build();
  }
}

package org.example.capstone.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.capstone.domain.Post;

@Getter @Setter
public class RequestCommentDto {
  private String content;
  private Long parentId;
}

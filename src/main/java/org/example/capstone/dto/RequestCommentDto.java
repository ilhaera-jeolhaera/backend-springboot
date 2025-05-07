package org.example.capstone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone.domain.Post;

@Getter @Setter
public class RequestCommentDto {
  @NotBlank(message = "내용은 필수입니다")
  private String content;
  private Long parentId;
}

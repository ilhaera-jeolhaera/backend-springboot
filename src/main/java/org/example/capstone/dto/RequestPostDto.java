package org.example.capstone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone.domain.Category;
import org.example.capstone.domain.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class RequestPostDto {
  @NotBlank(message = "제목은 필수입니다")
  private String title;
  @NotBlank(message = "내용은 필수입니다")
  private String content;
  @NotBlank(message = "카테고리는 필수입니다")
  private String category;
  @Builder.Default
  private List<MultipartFile> images = new ArrayList<>();
}

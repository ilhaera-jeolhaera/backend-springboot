package org.example.capstone.dto;

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
  private String title;
  private String content;
  private Category category;
  @Builder.Default
  private List<MultipartFile> images = new ArrayList<>();
}

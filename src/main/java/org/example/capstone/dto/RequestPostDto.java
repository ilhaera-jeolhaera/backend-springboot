package org.example.capstone.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.capstone.domain.Category;
import org.example.capstone.domain.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class RequestPostDto {
  private String title;
  private String content;
  private Category category;
  @Builder.Default
  private List<MultipartFile> images = new ArrayList<>(); // 이미지가 없을 때 null이 아닌 빈 객체로 저장

}

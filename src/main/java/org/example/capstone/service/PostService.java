package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.Post;
import org.example.capstone.repository.PostImageRepository;
import org.example.capstone.dto.RequestPostDto;
import org.example.capstone.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;

  public RequestPostDto createPost(RequestPostDto request, String username) {
    Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .category(request.getCategory())
            .username(username)
            .createdAt(LocalDateTime.now())
            .like(0)
            .images(new ArrayList<>())
            .build();
  }
}

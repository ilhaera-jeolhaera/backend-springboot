package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.Post;
import org.example.capstone.domain.PostImage;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.repository.PostImageRepository;
import org.example.capstone.dto.RequestPostDto;
import org.example.capstone.repository.PostRepository;
import org.example.capstone.util.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;
  private final FileUploader fileUploader;

  public ResponsePostDto createPost(RequestPostDto request, String username) throws IOException {
    Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .category(request.getCategory())
            .username(username)
            .createdAt(LocalDateTime.now())
            .likes(0)
            .images(new ArrayList<>())
            .build();

    for (MultipartFile file : request.getImages()) {
      String imageUrl = fileUploader.upload(file);
      PostImage image = PostImage.builder()
              .imageUrl(imageUrl)
              .post(post)
              .build();
      post.getImages().add(image);
    }

    Post savedPost = postRepository.save(post);
    return ResponsePostDto.from(savedPost);
  }
}

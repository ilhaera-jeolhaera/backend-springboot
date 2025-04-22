package org.example.capstone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.Category;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;
  private final FileUploader fileUploader;

  @Transactional
  public ResponsePostDto createPost(RequestPostDto request, String username) throws IOException {
    Post post = Post.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .category(Category.from(request.getCategory()))
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

  @Transactional(readOnly = true)
  public Page<ResponsePostDto> getAllPosts(String category, int page, int size, String sortBy) {
    Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    if (category != null && !category.isBlank()) {
      Category cat = Category.from(category);
      return postRepository.findByCategory(cat, pageable)
              .map(ResponsePostDto::from);
    } else {
      return postRepository.findAll(pageable)
              .map(ResponsePostDto::from);
    }
  }

  @Transactional(readOnly = true)
  public Optional<Post> getPost(Long id) {
    return postRepository.findById(id);
  }
}

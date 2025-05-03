package org.example.capstone.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.capstone.domain.LikePost;
import org.example.capstone.repository.LikePostRepository;
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
  private final LikePostRepository likePostRepository;
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
  public Page<ResponsePostDto> getAllPosts(String category, int page, String sortBy) {
    Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
    Pageable pageable = PageRequest.of(page, 10, sort);

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

  @Transactional
  public ResponsePostDto updatePost(Long postId, String username, RequestPostDto request) throws IOException {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다 : " + postId));

    if (!post.getUsername().equals(username)) {
      throw new IllegalStateException("유저가 일치하지 않습니다 : " + username);
    }

    post.setTitle(request.getTitle());
    post.setContent(request.getContent());
    post.setCategory(Category.from(request.getCategory()));

    if (request.getImages() != null && !request.getImages().isEmpty()) {
      List<PostImage> imagesToDelete = new ArrayList<>(post.getImages());
      for (PostImage image : imagesToDelete) {
        fileUploader.delete(image.getImageUrl());
        post.getImages().remove(image);
        postImageRepository.delete(image);
      }
    }

    if (request.getImages() != null && !request.getImages().isEmpty()) {
      for (MultipartFile file : request.getImages()) {
        if (!file.isEmpty()) {
          String imageUrl = fileUploader.upload(file);
          PostImage image = PostImage.builder()
                  .imageUrl(imageUrl)
                  .post(post)
                  .build();
          post.getImages().add(image);
        }
      }
    }

    Post updatedPost = postRepository.save(post);
    return ResponsePostDto.from(updatedPost);
  }

  @Transactional
  public void deletePost(Long postId, String username) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글입니다 : " + postId));

    if (!post.getUsername().equals(username)) {
      throw new IllegalStateException("유저가 일치하지 않습니다 : " + username);
    }

    postRepository.deleteById(postId);
  }

  @Transactional
  public ResponsePostDto likePost(Long postId, Long userId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다"));

    if(likePostRepository.existsByUserIdAndPostId(userId, postId)) {
      throw new IllegalStateException("이미 좋아요를 누른 게시글입니다");
    }

    post.setLikes(post.getLikes() + 1);
    likePostRepository.save(new LikePost(userId, postId));
    return ResponsePostDto.from(post);
  }
}

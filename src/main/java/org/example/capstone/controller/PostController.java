package org.example.capstone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.Post;
import org.example.capstone.dto.RequestPostDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @PostMapping
  public ResponseEntity<ResponsePostDto> createPost(
          @Valid @ModelAttribute RequestPostDto request,
          @RequestParam String username) throws IOException { // username은 임시
    if (request.getImages() == null) {
      request.setImages(new ArrayList<>());
    }
    ResponsePostDto response = postService.createPost(request, username);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public Page<ResponsePostDto> getAllPosts(
          @RequestParam(required = false) String category,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "createdAt") String sortBy
  ) {
    return postService.getAllPosts(category, page, sortBy);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponsePostDto> getPost(@PathVariable Long id) {
    return postService.getPost(id)
            .map(post -> ResponseEntity.ok(ResponsePostDto.from(post)))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping("/{postId}")
  public ResponseEntity<ResponsePostDto> updatePost(
          @PathVariable Long postId,
          @RequestParam String username,
          @ModelAttribute RequestPostDto request) throws IOException {
    ResponsePostDto response = postService.updatePost(postId, username, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestParam String username) {
    postService.deletePost(postId, username);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{postId}/like")
  public ResponseEntity<ResponsePostDto> likePost(
          @PathVariable Long postId,
          @RequestParam String username) {
    ResponsePostDto response = postService.likePost(postId, username);
    return ResponseEntity.ok(response);
  }

  @GetMapping("like")
  public Page<ResponsePostDto> getLikedPosts(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam String username) {
    return postService.getLikedPosts(username, page);
  }
}

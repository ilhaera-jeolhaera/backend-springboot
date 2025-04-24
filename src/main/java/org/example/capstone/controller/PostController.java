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
          @RequestParam String username) throws IOException {
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
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "createdAt") String sortBy
  ) {
    return postService.getAllPosts(category, page, size, sortBy);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponsePostDto> getPost(@PathVariable Long id) {
    return postService.getPost(id)
            .map(post -> ResponseEntity.ok(ResponsePostDto.from(post)))
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponsePostDto> updatePost(
          @PathVariable Long id,
          @ModelAttribute RequestPostDto request) throws IOException {
    ResponsePostDto response = postService.updatePost(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseEntity.ok().build();
  }
}

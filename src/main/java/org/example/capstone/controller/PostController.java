package org.example.capstone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.RequestPostDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.PostService;
import org.example.capstone.service.UserService;
import org.example.capstone.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @PostMapping
  public ResponseEntity<ResponsePostDto> createPost(
          @Valid @ModelAttribute RequestPostDto request,
          @RequestHeader("Authorization") String token) throws IOException {
    Long userId = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(userId);
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
          @RequestHeader("Authorization") String token,
          @ModelAttribute RequestPostDto request) throws IOException {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    ResponsePostDto response = postService.updatePost(postId, username, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(
          @PathVariable Long postId,
          @RequestHeader("Authorization") String token) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    postService.deletePost(postId, username);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{postId}/like")
  public ResponseEntity<ResponsePostDto> likePost(
          @PathVariable Long postId,
          @RequestHeader("Authorization") String token) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    ResponsePostDto response = postService.likePost(postId, username);
    return ResponseEntity.ok(response);
  }

  @GetMapping("like")
  public Page<ResponsePostDto> getLikedPosts(
          @RequestParam(defaultValue = "0") int page,
          @RequestHeader("Authorization") String token) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    return postService.getLikedPosts(username, page);
  }
}

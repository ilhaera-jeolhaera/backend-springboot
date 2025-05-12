package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.RequestCommentDto;
import org.example.capstone.dto.ResponseCommentDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.CommentService;
import org.example.capstone.service.UserService;
import org.example.capstone.util.JwtUtil;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @PostMapping("/{postId}")
  public ResponseEntity<ResponseCommentDto> addComment(
          @PathVariable Long postId,
          @RequestHeader("Authorization") String token,
          @ModelAttribute RequestCommentDto request) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    ResponseCommentDto response = commentService.addComment(request, username, postId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{postId}")
  public Page<ResponseCommentDto> getAllComments(@PathVariable Long postId,
                                                 @RequestParam(defaultValue = "0") int page) {
    return commentService.getAllComments(postId, page);
  }

  @PatchMapping("/{commentId}")
  public ResponseEntity<ResponseCommentDto> updateComment(
          @PathVariable Long commentId,
          @RequestHeader("Authorization") String token,
          @ModelAttribute RequestCommentDto request
  ) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    ResponseCommentDto response = commentService.updateComment(commentId, username, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String token
  ) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    commentService.deleteComment(commentId, username);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/replies/{commentId}")
  public ResponseEntity<ResponseCommentDto> addReply(@PathVariable Long commentId,
                                                     @RequestHeader("Authorization") String token,
                                                     @ModelAttribute RequestCommentDto request) {
    Long id = jwtUtil.extractUserId(token);
    String username = userService.GetUsernameById(id);
    ResponseCommentDto response = commentService.addReply(request, username, commentId);
    return ResponseEntity.ok(response);
  }

}

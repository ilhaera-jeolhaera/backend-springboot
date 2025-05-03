package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.RequestCommentDto;
import org.example.capstone.dto.ResponseCommentDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.CommentService;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @PostMapping("/{postId}")
  public ResponseEntity<ResponseCommentDto> addComment(
          @PathVariable Long postId,
          @RequestParam String username,
          @ModelAttribute RequestCommentDto request) {
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
          @RequestParam String username,
          @ModelAttribute RequestCommentDto request) {
    ResponseCommentDto response = commentService.updateComment(commentId, username, request);
    return ResponseEntity.ok(response);
  }
}

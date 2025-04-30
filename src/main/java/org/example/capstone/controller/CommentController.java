package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.RequestCommentDto;
import org.example.capstone.dto.ResponseCommentDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.CommentService;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;

  @PostMapping("/{postId}")
  public ResponseEntity<ResponseCommentDto> addComment(@ModelAttribute RequestCommentDto request,
                                                       @RequestParam String username,
                                                       @PathVariable Long postId) {
    ResponseCommentDto response = commentService.addComment(request, username, postId);
    return ResponseEntity.ok(response);
  }
}

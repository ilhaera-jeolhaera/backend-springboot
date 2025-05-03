package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.domain.Comment;
import org.example.capstone.domain.Post;
import org.example.capstone.dto.RequestCommentDto;
import org.example.capstone.dto.ResponseCommentDto;
import org.example.capstone.repository.CommentRepository;
import org.example.capstone.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Transactional
  public ResponseCommentDto addComment(RequestCommentDto request, String username, Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다 : " + postId));

    Comment parent = null;
    if (request.getParentId() != null) {
      parent = commentRepository.findById(request.getParentId())
              .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다 : " + request.getParentId()));
    }

    Comment comment = Comment.builder()
            .content(request.getContent())
            .postId(post)
            .parentId(parent)
            .username(username)
            .createdAt(LocalDateTime.now())
            .build();

    Comment savedComment = commentRepository.save(comment);
    return ResponseCommentDto.from(savedComment);
  }

  @Transactional(readOnly = true)
  public Page<ResponseCommentDto> getAllComments(Long postId, int page) {
    Pageable pageable = PageRequest.of(page, 10);

    return commentRepository.findByPostId_id(postId, pageable)
            .map(ResponseCommentDto::from);
  }

  @Transactional
  public ResponseCommentDto updateComment(Long commentId, String username, RequestCommentDto request) {
    Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다 : " + commentId));

    if (!comment.getUsername().equals(username)) {
      throw new IllegalStateException("유저가 일치하지 않습니다 : " + username);
    }

    comment.setContent(request.getContent());

    Comment updatedComment = commentRepository.save(comment);
    return ResponseCommentDto.from(updatedComment);
  }
}

package org.example.capstone.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.RequestPostDto;
import org.example.capstone.dto.ResponsePostDto;
import org.example.capstone.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;

@Controller
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

}

package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.service.PolicyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PolicyController {
  private final PolicyService apiService;

  @GetMapping("/policy/my")
  public String getPolicy() throws IOException {
    try {
    return apiService.getPolicies();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}

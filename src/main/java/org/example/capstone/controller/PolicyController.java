package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.service.PolicyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PolicyController {
  private final PolicyService apiService;

  @GetMapping("/policy/my")
  public String getPolicy() {
    try {
      return apiService.getPolicies();
    } catch (Exception e) {
      return "{\"error\":\"API 호출 실패: " + e.getMessage() + "\"}";
    }
  }
}

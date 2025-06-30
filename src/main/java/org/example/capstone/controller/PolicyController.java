package org.example.capstone.controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.service.PolicyService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PolicyController {
  private final PolicyService policyService;

  @GetMapping("/policy/my")
  public Page<PolicyDto> getPolicy(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "0") int progress,
          @RequestParam(defaultValue = "0") int startAge,
          @RequestParam(defaultValue = "100") int endAge,
          @RequestParam String organ
  ) {
    return policyService.getAllPolicies(page, progress, startAge, endAge, organ);
  }
}

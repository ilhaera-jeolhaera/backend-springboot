package org.example.capstone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.repository.PolicyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;
  private final ObjectMapper objectMapper;

  public Page<PolicyDto> getAllPolicies(int page) {
    Pageable pageable = PageRequest.of(page, 8);
    
    return policyRepository.findAll(pageable).map(PolicyDto::from);
  }
}

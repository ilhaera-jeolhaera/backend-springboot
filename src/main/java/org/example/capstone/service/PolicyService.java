package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;

  public Page<PolicyDto> getAllPolicies(int page, int progress, Integer startAge, Integer endAge, String organ) {
    Pageable pageable = PageRequest.of(page, 8);

    Page<Policy> policyPage = policyRepository.searchPolicies(organ, startAge, endAge, progress, pageable);
    return policyPage.map(PolicyDto::from);
  }
}

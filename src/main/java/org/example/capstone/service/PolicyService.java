package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;
import org.example.capstone.specification.PolicySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;

  public Page<PolicyDto> getAllPolicies(int page, int progress, Integer startAge, Integer endAge, String organ) {

    Pageable pageable = PageRequest.of(page, 8);

    Specification<Policy> spec = Specification
            .where(PolicySpecification.matchOrgan(organ))
            .and(PolicySpecification.ageCheck(startAge, endAge))
            .and(PolicySpecification.progressCheck(progress));

    Page<Policy> policyPage = policyRepository.findAll(spec, pageable);

    return policyPage.map(PolicyDto::from);

  }
}
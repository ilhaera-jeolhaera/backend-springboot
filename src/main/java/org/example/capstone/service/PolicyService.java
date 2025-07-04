package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;
import org.example.capstone.repository.PolicySpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;

  public Page<PolicyDto> getAllPolicies(int page, int progress, int startAge, int endAge, String organ) {
    Pageable pageable = PageRequest.of(page, 8);

    Specification<Policy> spec = Specification
            .where(PolicySpecification.matchOrgan(organ));

    Page<Policy> policyPage = policyRepository.findAll(spec, Pageable.unpaged());

    List<PolicyDto> filtered = policyPage.stream()
            .map(PolicyDto::from)
            .filter(dto -> {
              boolean progressCheck =
                      progress == 0
                              || (progress == 1 && dto.isEnd())
                              || (progress == 2 && !dto.isEnd());

              boolean ageCheck;
              if (startAge == 0 && endAge == 100) {
                ageCheck = true;
              } else {
                try {
                  int minAge = Integer.parseInt(dto.getSprtTrgtMinAge());
                  int maxAge = Integer.parseInt(dto.getSprtTrgtMaxAge());
                  ageCheck = minAge >= startAge && maxAge <= endAge;
                } catch (Exception e) {
                  ageCheck = false;
                }
              }

              return progressCheck && ageCheck;
            })
            .toList();

    int start = page * 8;
    int end = Math.min(start + 8, filtered.size());
    List<PolicyDto> pageContent = filtered.subList(start, end);

    return new PageImpl<>(pageContent, PageRequest.of(page, 8), filtered.size());
  }
}

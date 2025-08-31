package org.example.capstone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.capstone.dto.PolicyDto;
import org.example.capstone.entity.Policy;
import org.example.capstone.repository.PolicyRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyService {
  private final PolicyRepository policyRepository;

  public Page<PolicyDto> getAllPolicies(int page, int progress, Integer startAge, Integer endAge, String organ) {
    try {
      log.info("정책 조회 요청 - 페이지: {}, 진행상태: {}, 시작연령: {}, 종료연령: {}, 기관: {}", 
               page, progress, startAge, endAge, organ);
      
      Pageable pageable = PageRequest.of(page, 8);
      
      Page<Policy> policyPage = policyRepository.searchPolicies(organ, startAge, endAge, progress, pageable);
      log.info("조회된 정책 수: {}", policyPage.getTotalElements());
      
      return policyPage.map(PolicyDto::from);
      
    } catch (Exception e) {
      log.error("정책 조회 중 오류 발생: {}", e.getMessage(), e);
      throw e;
    }
  }
}
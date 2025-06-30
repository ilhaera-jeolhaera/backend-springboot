package org.example.capstone.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.capstone.entity.Policy;
import org.springframework.data.jpa.domain.Specification;

public class PolicySpecification {

  public static Specification<Policy> matchOrgan(String organ) {
    return (root, query, cb) -> {
      if (organ == null || organ.isEmpty()) return null;
      return cb.like(root.get("operInstCdNm"), "%" + organ + "%");
    };
  }

  public static Specification<Policy> matchAgeRange(int startAge, int endAge) {
    return (root, query, cb) -> {
      try {
        Predicate minCheck = cb.lessThanOrEqualTo(cb.literal(startAge), cb.toInteger(root.get("sprtTrgtMaxAge")));
        Predicate maxCheck = cb.greaterThanOrEqualTo(cb.literal(endAge), cb.toInteger(root.get("sprtTrgtMinAge")));
        return cb.and(minCheck, maxCheck);
      } catch (Exception e) {
        return null;
      }
    };
  }
}

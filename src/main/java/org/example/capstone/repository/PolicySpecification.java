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
}

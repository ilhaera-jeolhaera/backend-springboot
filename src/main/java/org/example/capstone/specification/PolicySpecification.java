package org.example.capstone.specification;

import jakarta.persistence.criteria.Predicate;
import org.example.capstone.entity.Policy;
import org.springframework.data.jpa.domain.Specification;


public class PolicySpecification {

  public static Specification<Policy> matchOrgan(String organ) {
    return (root, query, cb) -> {
      if (organ == null || organ.isEmpty()) return null;
      return cb.like(root.get("operInstCdNm"), "%" + organ + "%");
    };
  }

  public static Specification<Policy> ageCheck(Integer startAge, Integer endAge) {
    return (root, query, cb) -> {
      Predicate minAgePredicate = cb.greaterThanOrEqualTo(root.get("sprtTrgtMinAge"), startAge);
      Predicate maxAgePredicate = cb.lessThanOrEqualTo(root.get("sprtTrgtMaxAge"), endAge);
      return cb.and(minAgePredicate, maxAgePredicate);
    };
  }

  public static Specification<Policy> progressCheck(int progress) {
    return (root, query, cb) -> {
      if (progress == 1)
        return cb.equal(root.get("isEnd"), true);
      else if (progress == 2)
        return cb.equal(root.get("isEnd"), false);
      return null;
    };

  }

}
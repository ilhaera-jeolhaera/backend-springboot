package org.example.capstone.repository;

import org.example.capstone.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PolicyRepository extends JpaRepository<Policy, String>{
  @Query(
          value = """
    SELECT * 
    FROM policy p
    WHERE (:organ IS NULL OR MATCH(p.sprvsn_inst_cd_nm) AGAINST(:organ IN BOOLEAN MODE))
      AND (:startAge IS NULL OR p.sprt_trgt_min_age >= :startAge)
      AND (:endAge IS NULL OR p.sprt_trgt_max_age <= :endAge)
      AND (
        :progress = 0 
        OR (:progress = 1 AND p.is_end = true)
        OR (:progress = 2 AND p.is_end = false)
      )
    """,
          countQuery = """
    SELECT COUNT(*) 
    FROM policy p
    WHERE (:organ IS NULL OR MATCH(p.sprvsn_inst_cd_nm) AGAINST(:organ IN BOOLEAN MODE))
      AND (:startAge IS NULL OR p.sprt_trgt_min_age >= :startAge)
      AND (:endAge IS NULL OR p.sprt_trgt_max_age <= :endAge)
      AND (
        :progress = 0 
        OR (:progress = 1 AND p.is_end = true)
        OR (:progress = 2 AND p.is_end = false)
      )
    """,
          nativeQuery = true
  )
  Page<Policy> searchPolicies(
          @Param("organ") String organ,
          @Param("startAge") Integer startAge,
          @Param("endAge") Integer endAge,
          @Param("progress") int progress,
          Pageable pageable
  );

}

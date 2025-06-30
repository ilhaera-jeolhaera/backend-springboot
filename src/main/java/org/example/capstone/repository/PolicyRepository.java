package org.example.capstone.repository;

import org.example.capstone.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PolicyRepository extends JpaRepository<Policy, String>, JpaSpecificationExecutor<Policy> {
}

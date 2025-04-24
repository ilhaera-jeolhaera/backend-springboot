package org.example.capstone.repository;

import org.example.capstone.domain.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
  boolean existsByUserIdAndPostId(Long userId, Long postId);
}

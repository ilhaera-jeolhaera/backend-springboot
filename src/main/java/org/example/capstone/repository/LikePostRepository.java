package org.example.capstone.repository;

import org.example.capstone.domain.LikePost;
import org.example.capstone.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
  Page<LikePost> findByUsername(String username, Pageable pageable);
  boolean existsByUsernameAndPostId(String username, Long postId);
}

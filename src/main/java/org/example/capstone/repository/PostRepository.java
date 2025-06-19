package org.example.capstone.repository;

import org.example.capstone.entity.Category;
import org.example.capstone.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Page<Post> findByCategory(Category category, Pageable pageable);
}

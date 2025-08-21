package org.example.capstone.repository;

import org.example.capstone.dto.ResponseAllPostDto;
import org.example.capstone.entity.Category;
import org.example.capstone.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query(
          value = "SELECT new org.example.capstone.dto.ResponseAllPostDto(" +
                  "p.id, p.title, p.category, p.content, p.username, COUNT(l), p.createdAt) " +
                  "FROM Post p LEFT JOIN p.likes l " +
                  "WHERE p.category = :category " +
                  "GROUP BY p.id, p.title, p.category, p.content, p.username, p.createdAt",
          countQuery = "SELECT COUNT(p) FROM Post p WHERE p.category = :category"
  )
  Page<ResponseAllPostDto> findAllByCategoryWithLikeCount(
          @Param("category") Category category,
          Pageable pageable
  );

  @Query(
          value = "SELECT new org.example.capstone.dto.ResponseAllPostDto(" +
                  "p.id, p.title, p.category, p.content, p.username, COUNT(l), p.createdAt) " +
                  "FROM Post p LEFT JOIN p.likes l " +
                  "GROUP BY p.id, p.title, p.category, p.content, p.username, p.createdAt",
          countQuery = "SELECT COUNT(p) FROM Post p"
  )
  Page<ResponseAllPostDto> findAllWithLikeCount(Pageable pageable);
}

package com.example.blog.repository;

import com.example.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find published posts for homepage
    Page<Post> findByStatusOrderByCreateDateDesc(Integer status, Pageable pageable);

    // Find posts with a specific tag
    @Query("SELECT p FROM Post p WHERE p.status = :status AND p.tags LIKE %:tag%")
    Page<Post> findByTagAndStatus(@Param("tag") String tag, @Param("status") Integer status, Pageable pageable);

    // Find all posts by a user
    List<Post> findByUserIdOrderByCreateDateDesc(Long userId);
}
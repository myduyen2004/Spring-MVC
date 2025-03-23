package com.example.blog.repository;

import com.example.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find comments for a specific post
    List<Comment> findByPostIdAndStatusOrderByCreateDateDesc(Long postId, Integer status);

    // Find recent comments
    Page<Comment> findByStatusOrderByCreateDateDesc(Integer status, Pageable pageable);
}
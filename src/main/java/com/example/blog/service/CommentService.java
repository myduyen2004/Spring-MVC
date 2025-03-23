package com.example.blog.service;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findApprovedCommentsByPost(Post post) {
        return commentRepository.findByPostIdAndStatusOrderByCreateDateDesc(post.getId(), 2); // 2 = Approved
    }

    public Page<Comment> findRecentApprovedComments(int count) {
        Pageable pageable = PageRequest.of(0, count);
        return commentRepository.findByStatusOrderByCreateDateDesc(2, pageable); // 2 = Approved
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId, @ModelAttribute Comment comment) {
        Post post = postService.findById(postId);

        if (post != null) {
            comment.setPost(post);
            comment.setStatus(1); // 1 = Pending
            commentService.save(comment);
        }

        return "redirect:/post/" + postId;
    }
}
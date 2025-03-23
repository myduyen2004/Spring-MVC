package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Lookup;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.LookupService;
import com.example.blog.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LookupService lookupService;

    // Interceptor method to check if user is logged in
    @ModelAttribute
    public void checkAuth(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Unauthorized access");
        }
        model.addAttribute("currentUser", user);
    }

    @GetMapping("/posts")
    public String managePosts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Post> posts = postService.findByUser(user);
        model.addAttribute("posts", posts);

        // Add status lookups
        List<Lookup> statuses = lookupService.findByType("PostStatus");
        model.addAttribute("statuses", statuses);

        return "admin/posts";
    }

    @GetMapping("/post/create")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());

        // Add status lookups
        List<Lookup> statuses = lookupService.findByType("PostStatus");
        model.addAttribute("statuses", statuses);

        return "admin/createPost";
    }

    @PostMapping("/post/create")
    public String createPost(@ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        post.setUser(user);
        postService.save(post);

        return "redirect:/admin/posts";
    }

    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.findById(id);

        if (post == null || !post.getUser().getId().equals(user.getId())) {
            return "redirect:/admin/posts";
        }

        model.addAttribute("post", post);

        // Add status lookups
        List<Lookup> statuses = lookupService.findByType("PostStatus");
        model.addAttribute("statuses", statuses);

        return "admin/editPost";
    }

    @PostMapping("/post/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post existingPost = postService.findById(id);

        if (existingPost == null || !existingPost.getUser().getId().equals(user.getId())) {
            return "redirect:/admin/posts";
        }

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setStatus(post.getStatus());
        existingPost.setTags(post.getTags());

        postService.save(existingPost);

        return "redirect:/admin/posts";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.findById(id);

        if (post != null && post.getUser().getId().equals(user.getId())) {
            postService.delete(post);
        }

        return "redirect:/admin/posts";
    }

    @GetMapping("/comments")
    public String manageComments(Model model) {
        List<Comment> comments = commentService.findRecentApprovedComments(20).getContent();
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    @GetMapping("/comment/approve/{id}")
    public String approveComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            comment.setStatus(2); // 2 = Approved
            commentService.save(comment);
        }
        return "redirect:/admin/comments";
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        if (comment != null) {
            commentService.delete(comment);
        }
        return "redirect:/admin/comments";
    }
}
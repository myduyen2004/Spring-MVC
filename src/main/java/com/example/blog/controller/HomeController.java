package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        // Get posts for homepage
        Page<Post> postPage = postService.findPublishedPosts(page, 10);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());

        // Get recent comments
        Page<Comment> recentComments = commentService.findRecentApprovedComments(5);
        model.addAttribute("recentComments", recentComments.getContent());

        // Get tag cloud
        List<Tag> tags = tagService.findAllOrderByFrequency();
        model.addAttribute("tags", tags);

        Page<Comment> comments = commentService.findRecentApprovedComments(3);
        model.addAttribute("comments", comments.getContent());
        return "home";
    }


    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null || post.getStatus() != 2) { // 2 = Published
            return "redirect:/";
        }

        List<Comment> comments = commentService.findApprovedCommentsByPost(post);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());

        return "post";
    }

    @GetMapping("/tag/{tag}")
    public String tagPosts(@PathVariable String tag,
                           @RequestParam(defaultValue = "0") int page,
                           Model model) {
        Page<Post> postPage = postService.findByTag(tag, page, 10);

        model.addAttribute("tag", tag);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());

        return "tag";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
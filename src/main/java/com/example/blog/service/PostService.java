package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Page<Post> findPublishedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByStatusOrderByCreateDateDesc(2, pageable); // 2 = Published
    }

    public Page<Post> findByTag(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTagAndStatus(tag, 2, pageable); // 2 = Published
    }

    public List<Post> findByUser(User user) {
        return postRepository.findByUserIdOrderByCreateDateDesc(user.getId());
    }

    @Transactional
    public Post save(Post post) {
        // Handle tags
        if (post.getTags() != null && !post.getTags().isEmpty()) {
            // Process tags
            Set<String> tagNames = Arrays.stream(post.getTags().split(","))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toSet());

            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName);
                if (tag == null) {
                    tag = new Tag();
                    tag.setName(tagName);
                    tag.setFrequency(1);
                } else {
                    tag.incrementFrequency();
                }
                tagRepository.save(tag);
            }
        }

        return postRepository.save(post);
    }

    @Transactional
    public void delete(Post post) {
        // Handle tags before deletion
        if (post.getTags() != null && !post.getTags().isEmpty()) {
            Set<String> tagNames = Arrays.stream(post.getTags().split(","))
                    .map(String::trim)
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toSet());

            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName);
                if (tag != null) {
                    tag.decrementFrequency();
                    if (tag.getFrequency() <= 0) {
                        tagRepository.delete(tag);
                    } else {
                        tagRepository.save(tag);
                    }
                }
            }
        }

        postRepository.delete(post);
    }
}
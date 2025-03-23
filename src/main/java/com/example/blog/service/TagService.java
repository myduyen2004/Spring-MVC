package com.example.blog.service;

import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAllOrderByFrequency() {
        return tagRepository.findAllByOrderByFrequencyDesc();
    }
}
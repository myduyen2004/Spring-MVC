package com.example.blog.repository;

import com.example.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    // Find all tags ordered by frequency for tag cloud
    List<Tag> findAllByOrderByFrequencyDesc();
}
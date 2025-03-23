package com.example.blog.repository;

import com.example.blog.model.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long> {

    List<Lookup> findByTypeOrderByPosition(String type);

    Lookup findByTypeAndCode(String type, Integer code);
}
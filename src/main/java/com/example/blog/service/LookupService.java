package com.example.blog.service;

import com.example.blog.model.Lookup;
import com.example.blog.repository.LookupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookupService {

    @Autowired
    private LookupRepository lookupRepository;

    public List<Lookup> findByType(String type) {
        return lookupRepository.findByTypeOrderByPosition(type);
    }

    public Lookup findByTypeAndCode(String type, Integer code) {
        return lookupRepository.findByTypeAndCode(type, code);
    }
}
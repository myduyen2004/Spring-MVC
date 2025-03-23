package com.example.blog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer frequency;

    public Tag() {
        this.frequency = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    public void decrementFrequency() {
        if (this.frequency > 0) {
            this.frequency--;
        }
    }
}
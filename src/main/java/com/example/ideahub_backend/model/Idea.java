package com.example.ideahub_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ideas")
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private Double avgNovelty;
    private Double avgFeasibility;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;


    public Idea() {
    }

    public Idea(String title, String description, User author, Double avgNovelty, Double avgFeasibility, OffsetDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.avgNovelty = avgNovelty;
        this.avgFeasibility = avgFeasibility;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Double getAvgNovelty() {
        return avgNovelty;
    }

    public void setAvgNovelty(Double avgNovelty) {
        this.avgNovelty = avgNovelty;
    }

    public Double getAvgFeasibility() {
        return avgFeasibility;
    }

    public void setAvgFeasibility(Double avgFeasibility) {
        this.avgFeasibility = avgFeasibility;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

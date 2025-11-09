package com.example.ideahub_backend.dto;

import java.time.OffsetDateTime;

public class IdeaDto {
    private Long id;
    private String title;
    private String description;
    private UserDto author;
    private Double avgNovelty;
    private Double avgFeasibility;
    private OffsetDateTime createdAt;
    private Long commentsCount;

    public IdeaDto() {
    }

    public IdeaDto(Long id, String title, String description, UserDto author, Double avgNovelty, Double avgFeasibility, OffsetDateTime createdAt, Long commentsCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.avgNovelty = avgNovelty;
        this.avgFeasibility = avgFeasibility;
        this.createdAt = createdAt;
        this.commentsCount = commentsCount;
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

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }
}

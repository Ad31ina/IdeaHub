package com.example.ideahub_backend.dto;

import java.time.OffsetDateTime;

public class IdeaDto {
    private Long id;
    private String title;
    private String description;
    private UserDto author;
    private Double avgNovelty;
    private Double avgFeasibility;
    private Double averageRating;
    private OffsetDateTime createdAt;
    private Long commentsCount;
    private Long likes;
    private Long ratingsCount;
    private String trend;

    public IdeaDto() {
    }

    public IdeaDto(Long id,
                   String title,
                   String description,
                   UserDto author,
                   Double avgNovelty,
                   Double avgFeasibility,
                   Double averageRating,
                   OffsetDateTime createdAt,
                   Long commentsCount,
                   Long likes,
                   Long ratingsCount,
                   String trend) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.avgNovelty = avgNovelty;
        this.avgFeasibility = avgFeasibility;
        this.averageRating = averageRating;
        this.createdAt = createdAt;
        this.commentsCount = commentsCount;
        this.likes = likes;
        this.ratingsCount = ratingsCount;
        this.trend = trend;
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Long ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }
}

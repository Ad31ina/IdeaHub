package com.example.ideahub_backend.dto;

import java.time.OffsetDateTime;

public class CommentDto {
    private Long id;
    private Long ideaId;
    private UserDto author;
    private String text;
    private OffsetDateTime createdAt;

    public CommentDto() {
    }

    public CommentDto(Long id, Long ideaId, UserDto author, String text, OffsetDateTime createdAt) {
        this.id = id;
        this.ideaId = ideaId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.ideahub_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea ideaId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    public Comment() {
    }

    public Comment(User author, Idea ideaId, String text, OffsetDateTime createdAt) {
        this.author = author;
        this.ideaId = ideaId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Idea getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(Idea ideaId) {
        this.ideaId = ideaId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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

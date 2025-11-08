package com.example.ideahub_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea ideaId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(nullable = false)
    private Integer novelty;

    @Column(nullable = false)
    private Integer feasibility;

    public Rating() {
    }

    public Rating(Idea ideaId, User userId, Integer novelty, Integer feasibility) {
        this.ideaId = ideaId;
        this.userId = userId;
        this.novelty = novelty;
        this.feasibility = feasibility;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getNovelty() {
        return novelty;
    }

    public void setNovelty(Integer novelty) {
        this.novelty = novelty;
    }

    public Integer getFeasibility() {
        return feasibility;
    }

    public void setFeasibility(Integer feasibility) {
        this.feasibility = feasibility;
    }
}

package com.example.ideahub_backend.dto;
;

public class RatingDto {
    private Long ideaId;
    private Long userId;
    private Integer novelty;
    private Integer feasibility;

    public RatingDto() {
    }

    public RatingDto(Long ideaId, Long userId, Integer novelty, Integer feasibility) {
        this.ideaId = ideaId;
        this.userId = userId;
        this.novelty = novelty;
        this.feasibility = feasibility;
    }

    public Long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

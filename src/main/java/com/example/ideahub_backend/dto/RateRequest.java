package com.example.ideahub_backend.dto;

public class RateRequest {
    private Integer novelty;
    private Integer feasibility;

    public RateRequest() {
    }

    public RateRequest(Integer novelty, Integer feasibility) {
        this.novelty = novelty;
        this.feasibility = feasibility;
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





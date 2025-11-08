package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.Rating;
import com.example.ideahub_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByIdeaIdAndUserId(Idea ideaId, User userId);
    List<Rating> findByIdeaId(Idea ideaId);
}

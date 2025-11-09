package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.Rating;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.repository.IdeaRepository;
import com.example.ideahub_backend.repository.RatingRepository;
import com.example.ideahub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Idea rateIdea(Long ideaId, Long userId, Integer novelty, Integer feasibility) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (idea.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("Вы не можете оценить свою собственную идею");
        }

        if (novelty < 1 || novelty > 5 || feasibility < 1 || feasibility > 5) {
            throw new RuntimeException("Оценка должна быть от 1 до 5");
        }

        Optional<Rating> existingRating = ratingRepository.findByIdeaIdAndUserId(idea, user);

        if (existingRating.isPresent()) {
            throw new RuntimeException("Вы уже оценили эту идею. Один пользователь может оценить идею только один раз.");
        }

        Rating rating = new Rating(idea, user, novelty, feasibility);
        ratingRepository.save(rating);

        List<Rating> allRatings = ratingRepository.findByIdeaId(idea);
        double avgNovelty = allRatings.stream()
                .mapToInt(Rating::getNovelty)
                .average()
                .orElse(0.0);
        double avgFeasibility = allRatings.stream()
                .mapToInt(Rating::getFeasibility)
                .average()
                .orElse(0.0);

        idea.setAvgNovelty(avgNovelty);
        idea.setAvgFeasibility(avgFeasibility);
        ideaRepository.save(idea);

        Idea savedIdea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена после сохранения"));
        
        return savedIdea;
    }

    public boolean hasUserRated(Long ideaId, Long userId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        
        return ratingRepository.findByIdeaIdAndUserId(idea, user).isPresent();
    }
}


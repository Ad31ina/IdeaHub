package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.repository.CommentRepository;
import com.example.ideahub_backend.repository.IdeaRepository;
import com.example.ideahub_backend.repository.RatingRepository;
import com.example.ideahub_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {
    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public Idea createIdea(Idea idea, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        idea.setAuthor(author);
        idea.setAvgNovelty(0.0);
        idea.setAvgFeasibility(0.0);
        return ideaRepository.save(idea);
    }

    public List<Idea> getAllIdeas() {
        List<Idea> ideas = ideaRepository.findAll();
        ideas.forEach(idea -> {
            if (idea.getAvgNovelty() == null) {
                idea.setAvgNovelty(0.0);
            }
            if (idea.getAvgFeasibility() == null) {
                idea.setAvgFeasibility(0.0);
            }
        });
        return ideas;
    }

    public List<Idea> getTrendingIdeas() {
        List<Idea> ideas = ideaRepository.findAllOrderByTrending();
        ideas.forEach(idea -> {
            if (idea.getAvgNovelty() == null) {
                idea.setAvgNovelty(0.0);
            }
            if (idea.getAvgFeasibility() == null) {
                idea.setAvgFeasibility(0.0);
            }
        });
        return ideas;
    }

    public Optional<Idea> getIdeaById(Long id) {
        Optional<Idea> ideaOpt = ideaRepository.findById(id);
        ideaOpt.ifPresent(idea -> {
            if (idea.getAvgNovelty() == null) {
                idea.setAvgNovelty(0.0);
            }
            if (idea.getAvgFeasibility() == null) {
                idea.setAvgFeasibility(0.0);
            }
        });
        return ideaOpt;
    }

    public Idea updateIdea(Long ideaId, Idea updatedIdea, Long userId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        Long authorId = idea.getAuthor() != null ? idea.getAuthor().getId() : null;
        boolean isMatch = authorId != null && authorId.equals(userId);


        if (idea.getAuthor() == null || !isMatch) {
            throw new RuntimeException("Нет прав на редактирование этой идеи");
        }

        idea.setTitle(updatedIdea.getTitle());
        idea.setDescription(updatedIdea.getDescription());
        return ideaRepository.save(idea);
    }

    @Transactional
    public void deleteIdea(Long ideaId, Long userId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        Long authorId = idea.getAuthor() != null ? idea.getAuthor().getId() : null;
        boolean isMatch = authorId != null && authorId.equals(userId);

        if (idea.getAuthor() == null || !isMatch) {
            throw new RuntimeException("Нет прав на удаление этой идеи");
        }

        List<com.example.ideahub_backend.model.Comment> comments = commentRepository.findByIdeaId(idea);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }

        List<com.example.ideahub_backend.model.Rating> ratings = ratingRepository.findByIdeaId(idea);
        if (!ratings.isEmpty()) {
            ratingRepository.deleteAll(ratings);
        }

        ideaRepository.delete(idea);
    }
}

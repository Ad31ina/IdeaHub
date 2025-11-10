package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.Comment;
import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.Rating;
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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Idea> ideas = ideaRepository.findAll();
        Map<Long, TrendStats> statsByIdea = new HashMap<>();

        for (Idea idea : ideas) {
            if (idea.getAvgNovelty() == null) {
                idea.setAvgNovelty(0.0);
            }
            if (idea.getAvgFeasibility() == null) {
                idea.setAvgFeasibility(0.0);
            }

            long ratingsCount = ratingRepository.countByIdeaId(idea);
            long commentsCount = commentRepository.countByIdeaId(idea);
            double averageRating = 0.0;
            if (ratingsCount > 0) {
                averageRating = (idea.getAvgNovelty() + idea.getAvgFeasibility()) / 2.0;
            }

            double trendScore = (averageRating * ratingsCount) + commentsCount;
            statsByIdea.put(idea.getId(), new TrendStats(trendScore, ratingsCount, commentsCount));
        }

        ideas.sort((a, b) -> {
            TrendStats statsA = statsByIdea.get(a.getId());
            TrendStats statsB = statsByIdea.get(b.getId());

            int scoreCompare = Double.compare(statsB.trendScore(), statsA.trendScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }

            int ratingsCompare = Long.compare(statsB.ratingsCount(), statsA.ratingsCount());
            if (ratingsCompare != 0) {
                return ratingsCompare;
            }

            int commentsCompare = Long.compare(statsB.commentsCount(), statsA.commentsCount());
            if (commentsCompare != 0) {
                return commentsCompare;
            }

            OffsetDateTime createdAtA = a.getCreatedAt();
            OffsetDateTime createdAtB = b.getCreatedAt();
            Instant instantA = createdAtA != null ? createdAtA.toInstant() : Instant.EPOCH;
            Instant instantB = createdAtB != null ? createdAtB.toInstant() : Instant.EPOCH;
            return instantB.compareTo(instantA);
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

        List<Comment> comments = commentRepository.findByIdeaId(idea);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }

        List<Rating> ratings = ratingRepository.findByIdeaId(idea);
        if (!ratings.isEmpty()) {
            ratingRepository.deleteAll(ratings);
        }

        ideaRepository.delete(idea);
    }

    private record TrendStats(double trendScore, long ratingsCount, long commentsCount) {
    }
}

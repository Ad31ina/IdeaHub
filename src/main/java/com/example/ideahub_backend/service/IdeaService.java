package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.repository.IdeaRepository;
import com.example.ideahub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {
    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private UserRepository userRepository;

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

        if (!idea.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("Нет прав на редактирование этой идеи");
        }

        idea.setTitle(updatedIdea.getTitle());
        idea.setDescription(updatedIdea.getDescription());
        return ideaRepository.save(idea);
    }

    public void deleteIdea(Long ideaId, Long userId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        if (!idea.getAuthor().getId().equals(userId)) {
            throw new RuntimeException("Нет прав на удаление этой идеи");
        }

        ideaRepository.delete(idea);
    }
}

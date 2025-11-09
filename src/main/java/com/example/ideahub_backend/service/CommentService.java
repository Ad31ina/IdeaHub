package com.example.ideahub_backend.service;

import com.example.ideahub_backend.model.Comment;
import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.User;
import com.example.ideahub_backend.repository.CommentRepository;
import com.example.ideahub_backend.repository.IdeaRepository;
import com.example.ideahub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long ideaId, Long userId, String text) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (text == null || text.trim().isEmpty()) {
            throw new RuntimeException("Текст комментария не может быть пустым");
        }

        if (text.length() > 500) {
            throw new RuntimeException("Текст комментария не может превышать 500 символов");
        }

        Comment comment = new Comment(user, idea, text.trim(), null);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByIdeaId(Long ideaId) {
        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Идея не найдена"));

        return commentRepository.findByIdeaId(idea);
    }
}



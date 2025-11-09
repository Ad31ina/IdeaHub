package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.Comment;
import com.example.ideahub_backend.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdeaId(Idea ideaId);
    long countByIdeaId(Idea ideaId);
}

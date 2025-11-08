package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    Page<Idea> findAll(Pageable pageable);
    Page<Idea> findByAuthor(User author, Pageable pageable);
}

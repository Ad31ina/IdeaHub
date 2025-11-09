package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findAll();
}

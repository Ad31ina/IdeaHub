package com.example.ideahub_backend.repository;

import com.example.ideahub_backend.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findAll();

    @Query("""
            SELECT i
            FROM Idea i
            ORDER BY
                (((COALESCE(i.avgNovelty, 0) + COALESCE(i.avgFeasibility, 0)) / 2.0)
                    * (SELECT COUNT(r) FROM Rating r WHERE r.ideaId = i))
                + (SELECT COUNT(c) FROM Comment c WHERE c.ideaId = i) DESC,
                (SELECT COUNT(r) FROM Rating r WHERE r.ideaId = i) DESC,
                (SELECT COUNT(c) FROM Comment c WHERE c.ideaId = i) DESC,
                i.createdAt DESC
            """)
    List<Idea> findAllOrderByTrending();
}

package com.example.ideahub_backend.controller;

import com.example.ideahub_backend.dto.*;
import com.example.ideahub_backend.model.Comment;
import com.example.ideahub_backend.model.Idea;
import com.example.ideahub_backend.repository.CommentRepository;
import com.example.ideahub_backend.service.CommentService;
import com.example.ideahub_backend.service.IdeaService;
import com.example.ideahub_backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;


    @PostMapping
    public ResponseEntity<IdeaDto> createIdea(@RequestBody Idea idea, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Idea savedIdea = ideaService.createIdea(idea, userId);
        return ResponseEntity.ok(mapToDto(savedIdea));
    }

    @GetMapping
    public ResponseEntity<List<IdeaDto>> getAllIdeas(@RequestParam(required = false) String sort) {
        List<Idea> ideas;
        if ("trending".equals(sort)) {
            ideas = ideaService.getTrendingIdeas();
        } else {
            ideas = ideaService.getAllIdeas();
        }
        
        List<IdeaDto> ideaDtos = ideas.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ideaDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdeaDto> getIdeaById(@PathVariable Long id) {
        return ideaService.getIdeaById(id)
                .map(this::mapToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdeaDto> updateIdea(@PathVariable Long id,
                                              @RequestBody Idea idea,
                                              Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Idea updated = ideaService.updateIdea(id, idea, userId);
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdea(@PathVariable Long id,
                                           Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        ideaService.deleteIdea(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<IdeaDto> rateIdea(@PathVariable Long id,
                                             @RequestBody RateRequest request,
                                             Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Idea ratedIdea = ratingService.rateIdea(id, userId, request.getNovelty(), request.getFeasibility());
        return ResponseEntity.ok(mapToDto(ratedIdea));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long id,
                                                 @RequestBody CommentRequest request,
                                                 Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Comment comment = commentService.addComment(id, userId, request.getText());
        return ResponseEntity.ok(mapCommentToDto(comment));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByIdeaId(id);
        List<CommentDto> commentDtos = comments.stream()
                .map(this::mapCommentToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDtos);
    }

    @GetMapping("/{id}/has-rated")
    public ResponseEntity<Boolean> hasUserRated(@PathVariable Long id, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        boolean hasRated = ratingService.hasUserRated(id, userId);
        return ResponseEntity.ok(hasRated);
    }

    private IdeaDto mapToDto(Idea idea) {
        UserDto author = new UserDto(
                idea.getAuthor().getId(),
                idea.getAuthor().getUsername(),
                idea.getAuthor().getEmail()
        );
        Double avgNovelty = idea.getAvgNovelty() != null ? idea.getAvgNovelty() : 0.0;
        Double avgFeasibility = idea.getAvgFeasibility() != null ? idea.getAvgFeasibility() : 0.0;

        long commentsCount = commentRepository.countByIdeaId(idea);
        
        return new IdeaDto(
                idea.getId(),
                idea.getTitle(),
                idea.getDescription(),
                author,
                avgNovelty,
                avgFeasibility,
                idea.getCreatedAt(),
                commentsCount
        );
    }

    private CommentDto mapCommentToDto(Comment comment) {
        UserDto author = new UserDto(
                comment.getAuthor().getId(),
                comment.getAuthor().getUsername(),
                comment.getAuthor().getEmail()
        );
        return new CommentDto(
                comment.getId(),
                comment.getIdeaId().getId(),
                author,
                comment.getText(),
                comment.getCreatedAt()
        );
    }
}

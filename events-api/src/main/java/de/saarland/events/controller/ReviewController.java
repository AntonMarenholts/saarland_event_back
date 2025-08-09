package de.saarland.events.controller;

import de.saarland.events.dto.ReviewRequestDto;
import de.saarland.events.dto.ReviewResponseDto;
import de.saarland.events.mapper.ReviewMapper;
import de.saarland.events.model.Review;
import de.saarland.events.security.services.UserDetailsImpl;
import de.saarland.events.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events/{eventId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getEventReviews(@PathVariable Long eventId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsForEvent(eventId).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Только для авторизованных пользователей
    public ResponseEntity<ReviewResponseDto> addReview(@PathVariable Long eventId,
                                                       @Valid @RequestBody ReviewRequestDto reviewRequestDto,
                                                       Authentication authentication) {
        // Получаем ID пользователя из текущей сессии
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        Review createdReview = reviewService.createReview(eventId, userId, reviewRequestDto);
        return new ResponseEntity<>(reviewMapper.toDto(createdReview), HttpStatus.CREATED);
    }
}
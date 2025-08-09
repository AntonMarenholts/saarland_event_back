package de.saarland.events.service;

import de.saarland.events.dto.ReviewRequestDto;
import de.saarland.events.model.Event;
import de.saarland.events.model.Review;
import de.saarland.events.model.User;
import de.saarland.events.repository.EventRepository;
import de.saarland.events.repository.ReviewRepository;
import de.saarland.events.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsForEvent(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }

    @Transactional
    public Review createReview(Long eventId, Long userId, ReviewRequestDto reviewDto) {
        // 1. Проверяем, существуют ли событие и пользователь
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // 2. Проверяем, что событие уже прошло
        if (event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("You can only review past events.");
        }

        // 3. Проверяем, не оставлял ли пользователь уже отзыв
        reviewRepository.findByEventIdAndUserId(eventId, userId).ifPresent(r -> {
            throw new IllegalArgumentException("User has already reviewed this event.");
        });

        // 4. Если все проверки пройдены, создаем и сохраняем отзыв
        Review review = new Review();
        review.setEvent(event);
        review.setUser(user);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }
}
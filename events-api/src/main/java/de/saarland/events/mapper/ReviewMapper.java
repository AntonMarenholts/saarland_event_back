package de.saarland.events.mapper;

import de.saarland.events.dto.ReviewResponseDto;
import de.saarland.events.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDto toDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUsername(review.getUser().getUsername()); // Берем имя из связанной сущности User
        dto.setUserId(review.getUser().getId());
        return dto;
    }
}
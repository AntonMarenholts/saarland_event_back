package de.saarland.events.repository;

import de.saarland.events.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEventId(Long eventId);
    Optional<Review> findByEventIdAndUserId(Long eventId, Long userId);
}
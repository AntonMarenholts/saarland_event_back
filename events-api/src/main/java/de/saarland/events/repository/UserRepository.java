// src/main/java/de/saarland/events/repository/UserRepository.java

package de.saarland.events.repository;

import de.saarland.events.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.saarland.events.model.Event;
import java.util.List;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findByFavoriteEventsContains(Event event);
}
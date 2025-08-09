// src/main/java/de/saarland/events/service/FavoriteService.java

package de.saarland.events.service;

import de.saarland.events.model.Event;
import de.saarland.events.model.User;
import de.saarland.events.repository.EventRepository;
import de.saarland.events.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class FavoriteService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public FavoriteService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void addFavorite(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        user.getFavoriteEvents().add(event);
        userRepository.save(user);
    }

    @Transactional
    public void removeFavorite(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        user.getFavoriteEvents().remove(event);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<Event> getFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return user.getFavoriteEvents();
    }
}
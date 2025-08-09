// src/main/java/de/saarland/events/controller/FavoriteController.java

package de.saarland.events.controller;

import de.saarland.events.dto.EventResponseDto;
import de.saarland.events.mapper.EventMapper;
import de.saarland.events.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final EventMapper eventMapper;

    public FavoriteController(FavoriteService favoriteService, EventMapper eventMapper) {
        this.favoriteService = favoriteService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<List<EventResponseDto>> getFavorites(@PathVariable Long userId) {
        List<EventResponseDto> favoriteEvents = favoriteService.getFavorites(userId).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoriteEvents);
    }

    @PostMapping("/{userId}/{eventId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> addFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        favoriteService.addFavorite(userId, eventId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{eventId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long eventId) {
        favoriteService.removeFavorite(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
package de.saarland.events.controller;

import de.saarland.events.dto.EventRequestDto;
import de.saarland.events.dto.EventResponseDto;
import de.saarland.events.dto.StatusUpdateRequest;
import de.saarland.events.mapper.EventMapper;
import de.saarland.events.model.EStatus;
import de.saarland.events.model.Event;
import de.saarland.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import de.saarland.events.dto.AdminStatsDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/events")
public class AdminEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public AdminEventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventResponseDto>> getAllEventsForAdmin() {
        List<Event> events = eventService.findAllEventsForAdmin();
        List<EventResponseDto> eventDtos = events.stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventDtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // <-- Убеждаемся, что эта аннотация на месте
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        Event eventToCreate = eventMapper.toEntity(eventRequestDto);
        Event createdEvent = eventService.createEvent(eventToCreate, eventRequestDto.getCategoryId(), eventRequestDto.getCityId());
        EventResponseDto responseDto = eventMapper.toResponseDto(createdEvent);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // <-- Убеждаемся, что эта аннотация на месте
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // <-- Убеждаемся, что эта аннотация на месте
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequestDto eventRequestDto) {
        Event eventData = eventMapper.toEntity(eventRequestDto);
        Event updatedEvent = eventService.updateEvent(id, eventData, eventRequestDto.getCategoryId(), eventRequestDto.getCityId());
        EventResponseDto responseDto = eventMapper.toResponseDto(updatedEvent);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") // <-- Убеждаемся, что эта аннотация на месте
    public ResponseEntity<EventResponseDto> updateEventStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        EStatus newStatus = EStatus.valueOf(request.getStatus().toUpperCase());
        Event updatedEvent = eventService.updateEventStatus(id, newStatus);
        return ResponseEntity.ok(eventMapper.toResponseDto(updatedEvent));
    }
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatsDto> getStatistics() {
        AdminStatsDto stats = eventService.getAdminStatistics();
        return ResponseEntity.ok(stats);
    }
}
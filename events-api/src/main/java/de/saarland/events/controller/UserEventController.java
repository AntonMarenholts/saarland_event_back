// src/main/java/de/saarland/events/controller/UserEventController.java

package de.saarland.events.controller;

import de.saarland.events.dto.EventRequestDto;
import de.saarland.events.dto.EventResponseDto;
import de.saarland.events.mapper.EventMapper;
import de.saarland.events.model.Event;
import de.saarland.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/events")
public class UserEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public UserEventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<EventResponseDto> submitEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        Event eventToCreate = eventMapper.toEntity(eventRequestDto);
        Event createdEvent = eventService.createEvent(eventToCreate, eventRequestDto.getCategoryId(), eventRequestDto.getCityId());
        EventResponseDto responseDto = eventMapper.toResponseDto(createdEvent);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
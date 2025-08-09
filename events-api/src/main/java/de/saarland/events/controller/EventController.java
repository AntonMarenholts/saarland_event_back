package de.saarland.events.controller;

import de.saarland.events.dto.EventResponseDto;
import de.saarland.events.mapper.EventMapper;
import de.saarland.events.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents(
            @RequestParam Optional<String> city,
            @RequestParam Optional<Long> category,
            @RequestParam Optional<Integer> year,
            @RequestParam Optional<Integer> month,
            @RequestParam Optional<String> categoryName // <-- НОВЫЙ ПАРАМЕТР
    ) {
        List<EventResponseDto> events = eventService.findEvents(city, category, year, month, categoryName)
                .stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        EventResponseDto eventDto = eventMapper.toResponseDto(eventService.getEventById(id));
        return ResponseEntity.ok(eventDto);
    }
}
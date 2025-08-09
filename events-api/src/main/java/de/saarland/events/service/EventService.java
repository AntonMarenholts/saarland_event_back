package de.saarland.events.service;

import de.saarland.events.dto.AdminStatsDto;
import de.saarland.events.model.*;
import de.saarland.events.repository.CategoryRepository;
import de.saarland.events.repository.CityRepository;
import de.saarland.events.repository.EventRepository;
import de.saarland.events.repository.UserRepository;
import de.saarland.events.specification.EventSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    // ... поля класса остаются без изменений ...
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    private final EventSpecification eventSpecification;
    private final UserRepository userRepository;


    public EventService(EventRepository eventRepository, CategoryRepository categoryRepository, CityRepository cityRepository, EventSpecification eventSpecification, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.cityRepository = cityRepository;
        this.eventSpecification = eventSpecification;
        this.userRepository = userRepository;
    }

    // ▼▼▼ ОБНОВЛЯЕМ МЕТОД findEvents ▼▼▼
    @Transactional(readOnly = true)
    public List<Event> findEvents(Optional<String> city, Optional<Long> categoryId, Optional<Integer> year, Optional<Integer> month, Optional<String> categoryName) {
        Specification<Event> spec = eventSpecification.findByCriteria(city, categoryId, year, month, categoryName);
        return eventRepository.findAll(spec);
    }

    // ... все остальные методы остаются без изменений ...
    @Transactional(readOnly = true)
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Событие с ID " + id + " не найдено"));
    }

    @Transactional
    public Event createEvent(Event event, Long categoryId, Long cityId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Категория с ID " + categoryId + " не найдена"));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("Город с ID " + cityId + " не найден"));

        event.setCategory(category);
        event.setCity(city);
        event.getTranslations().forEach(translation -> translation.setEvent(event));
        event.setStatus(EStatus.PENDING);
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event eventToDelete = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно удалить. Событие с ID " + id + " не найдено."));
        List<User> usersWithFavorite = userRepository.findByFavoriteEventsContains(eventToDelete);
        for (User user : usersWithFavorite) {
            user.getFavoriteEvents().remove(eventToDelete);
        }
        eventRepository.delete(eventToDelete);
    }

    @Transactional
    public Event updateEvent(Long eventId, Event updatedEventData, Long categoryId, Long cityId) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие с ID " + eventId + " не найдено"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Категория с ID " + categoryId + " не найдена"));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("Город с ID " + cityId + " не найден"));

        existingEvent.setEventDate(updatedEventData.getEventDate());
        existingEvent.setCity(city);
        existingEvent.setImageUrl(updatedEventData.getImageUrl());
        existingEvent.setCategory(category);

        existingEvent.getTranslations().clear();
        updatedEventData.getTranslations().forEach(translation -> {
            translation.setEvent(existingEvent);
            existingEvent.getTranslations().add(translation);
        });

        return eventRepository.save(existingEvent);
    }

    @Transactional
    public Event updateEventStatus(Long eventId, EStatus status) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие с ID " + eventId + " не найдено"));
        existingEvent.setStatus(status);
        return eventRepository.save(existingEvent);
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventsForAdmin() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public AdminStatsDto getAdminStatistics() {
        long totalEvents = eventRepository.count();
        long pendingEvents = eventRepository.countByStatus(EStatus.PENDING);
        long approvedEvents = eventRepository.countByStatus(EStatus.APPROVED);
        long totalUsers = userRepository.count();
        long totalCategories = categoryRepository.count();

        return new AdminStatsDto(totalEvents, pendingEvents, approvedEvents, totalUsers, totalCategories);
    }
}
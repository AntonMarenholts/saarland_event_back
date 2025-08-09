package de.saarland.events.service;

import de.saarland.events.model.Event;
import de.saarland.events.model.Reminder;
import de.saarland.events.model.User;
import de.saarland.events.repository.EventRepository;
import de.saarland.events.repository.ReminderRepository;
import de.saarland.events.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public ReminderService(ReminderRepository reminderRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void createReminder(Long userId, Long eventId, LocalDateTime remindAt) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Проверяем, чтобы дата напоминания была раньше даты события
        if (remindAt.isAfter(event.getEventDate())) {
            throw new IllegalArgumentException("Дата напоминания не может быть позже даты события.");
        }

        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setEvent(event);
        reminder.setRemindAt(remindAt);
        reminder.setSent(false); // По умолчанию, оно не отправлено

        reminderRepository.save(reminder);
    }
}
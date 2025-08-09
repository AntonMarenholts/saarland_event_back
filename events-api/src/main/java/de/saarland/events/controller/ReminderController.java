package de.saarland.events.controller;

import de.saarland.events.service.ReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    // DTO для запроса
    public static class ReminderRequest {
        public Long userId;
        public Long eventId;
        public LocalDateTime remindAt;
    }

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id") // Пользователь может создавать напоминание только для себя
    public ResponseEntity<?> setReminder(@RequestBody ReminderRequest request) {
        reminderService.createReminder(request.userId, request.eventId, request.remindAt);
        return ResponseEntity.ok(Map.of("message", "Напоминание успешно установлено!"));
    }
}
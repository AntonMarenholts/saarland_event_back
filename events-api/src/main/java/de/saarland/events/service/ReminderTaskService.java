package de.saarland.events.service;

import de.saarland.events.model.Reminder;
import de.saarland.events.repository.ReminderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderTaskService {

    private final ReminderRepository reminderRepository;
    private final EmailService emailService;

    public ReminderTaskService(ReminderRepository reminderRepository, EmailService emailService) {
        this.reminderRepository = reminderRepository;
        this.emailService = emailService;
    }

    // Запускать эту задачу каждую минуту
    // cron = "0 * * * * ?" означает "в 0 секунд каждой минуты"
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void processReminders() {
        System.out.println("Проверка напоминаний... " + LocalDateTime.now());

        // 1. Находим все напоминания, время которых уже наступило
        List<Reminder> dueReminders = reminderRepository.findAllByRemindAtBeforeAndIsSentFalse(LocalDateTime.now());

        if (dueReminders.isEmpty()) {
            return; // Если отправлять нечего, выходим
        }

        System.out.printf("Найдено %d напоминаний для отправки.\n", dueReminders.size());

        // 2. Проходим по каждому и отправляем email
        for (Reminder reminder : dueReminders) {
            emailService.sendReminderEmail(reminder.getUser(), reminder.getEvent());
            // 3. Помечаем как отправленное, чтобы не отправить снова
            reminder.setSent(true);
            reminderRepository.save(reminder);
        }
    }
}
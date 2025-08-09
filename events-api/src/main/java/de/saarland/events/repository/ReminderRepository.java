package de.saarland.events.repository;

import de.saarland.events.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    // Этот метод найдет все напоминания, которые нужно отправить прямо сейчас
    // и которые еще не были отправлены.
    List<Reminder> findAllByRemindAtBeforeAndIsSentFalse(LocalDateTime currentTime);
}
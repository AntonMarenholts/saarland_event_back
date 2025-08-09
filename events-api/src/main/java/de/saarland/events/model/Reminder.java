package de.saarland.events.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private LocalDateTime remindAt; // Время, когда нужно отправить напоминание

    @Column(nullable = false)
    private boolean isSent = false; // Отметка, что напоминание уже отправлено

    // Конструкторы, геттеры и сеттеры
    public Reminder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public LocalDateTime getRemindAt() { return remindAt; }
    public void setRemindAt(LocalDateTime remindAt) { this.remindAt = remindAt; }
    public boolean isSent() { return isSent; }
    public void setSent(boolean sent) { isSent = sent; }
}
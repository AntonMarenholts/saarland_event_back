package de.saarland.events.repository;

import de.saarland.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // <-- 1. Импортируем
import org.springframework.stereotype.Repository;
import de.saarland.events.model.EStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
// 2. Добавляем JpaSpecificationExecutor<Event>
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDateTime date);
    long countByStatus(EStatus status);
}
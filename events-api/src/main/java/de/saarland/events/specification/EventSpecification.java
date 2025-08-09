package de.saarland.events.specification;

import de.saarland.events.model.EStatus;
import de.saarland.events.model.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventSpecification {

    public Specification<Event> findByCriteria(
            Optional<String> cityName,
            Optional<Long> categoryId,
            Optional<Integer> year,
            Optional<Integer> month,
            Optional<String> categoryName // <-- НОВЫЙ ПАРАМЕТР
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), EStatus.APPROVED));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.now()));

            if (year.isEmpty() && month.isEmpty()) {
                LocalDateTime twoYearsFromNow = LocalDateTime.now().plusMonths(24);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), twoYearsFromNow));
            }

            // Фильтры
            cityName.ifPresent(c -> predicates.add(criteriaBuilder.equal(root.get("city").get("name"), c)));
            categoryId.ifPresent(id -> predicates.add(criteriaBuilder.equal(root.get("category").get("id"), id)));

            // ▼▼▼ НОВЫЙ ФИЛЬТР ПО НАЗВАНИЮ КАТЕГОРИИ ▼▼▼
            categoryName.ifPresent(name -> predicates.add(criteriaBuilder.equal(root.get("category").get("name"), name)));

            // Фильтр по дате
            if (year.isPresent() && month.isPresent()) {
                LocalDateTime startDate = LocalDateTime.of(year.get(), month.get(), 1, 0, 0);
                LocalDateTime endDate = startDate.plusMonths(1);
                predicates.add(criteriaBuilder.between(root.get("eventDate"), startDate, endDate));
            } else if (year.isPresent()) {
                LocalDateTime startDate = LocalDateTime.of(year.get(), 1, 1, 0, 0);
                LocalDateTime endDate = startDate.plusYears(1);
                predicates.add(criteriaBuilder.between(root.get("eventDate"), startDate, endDate));
            }

            query.orderBy(criteriaBuilder.asc(root.get("eventDate")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
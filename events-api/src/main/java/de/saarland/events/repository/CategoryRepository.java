package de.saarland.events.repository;

import de.saarland.events.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Spring Data JPA автоматически создаст SQL-запрос по названию этого метода
    Optional<Category> findByName(String name);
}
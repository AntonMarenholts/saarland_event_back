package de.saarland.events.mapper;

import de.saarland.events.dto.CategoryDto;
import de.saarland.events.model.Category;
import org.springframework.stereotype.Component;

@Component // Помечаем как компонент Spring, чтобы его можно было внедрять в другие классы
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        // Мы не устанавливаем ID, так как при создании новой категории ID генерируется базой
        return new Category(dto.getName(), dto.getDescription());
    }
}
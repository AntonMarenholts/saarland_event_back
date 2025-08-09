package de.saarland.events.service;

import de.saarland.events.model.Category;
import de.saarland.events.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- 1. ДОБАВИТЬ ЭТОТ ИМПОРТ

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // --- ИСПРАВЛЕНИЕ: Добавляем аннотацию @Transactional ---
    @Transactional(readOnly = true) // Указываем, что это транзакция только для чтения
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // --- ИСПРАВЛЕНИЕ: Добавляем аннотацию @Transactional ---
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        // Проверяем, существует ли категория с таким же именем
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            // Если существует, выбрасываем исключение
            throw new IllegalArgumentException("Категория с названием '" + category.getName() + "' уже существует.");
        }
        return categoryRepository.save(category);
    }

    // Здесь @Transactional не нужен
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория с ID " + id + " не найдена"));

        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(existingCategory);
    }
}
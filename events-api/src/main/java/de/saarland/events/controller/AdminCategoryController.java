package de.saarland.events.controller;

import de.saarland.events.dto.CategoryDto;
import de.saarland.events.mapper.CategoryMapper;
import de.saarland.events.model.Category;
import de.saarland.events.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public AdminCategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

}
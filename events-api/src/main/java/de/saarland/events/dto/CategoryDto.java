package de.saarland.events.dto;

// import lombok.Data; // Убираем Data

// @Data // Убрали!
public class CategoryDto {
    private Long id;
    private String name;
    private String description;

    // --- ЯВНО ДОБАВЛЕННЫЕ ГЕТТЕРЫ И СЕТТЕРЫ ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
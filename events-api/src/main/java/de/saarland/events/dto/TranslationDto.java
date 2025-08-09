package de.saarland.events.dto;

// import lombok.Data; // Убираем Data

// @Data // Убрали!
public class TranslationDto {
    private String locale;
    private String name;
    private String description;

    // --- ЯВНО ДОБАВЛЕННЫЕ ГЕТТЕРЫ И СЕТТЕРЫ ---

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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
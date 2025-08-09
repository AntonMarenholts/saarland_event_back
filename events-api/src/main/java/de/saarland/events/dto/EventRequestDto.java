package de.saarland.events.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class EventRequestDto {

    @NotNull(message = "Дата события не может быть пустой")
    @Future(message = "Дата события должна быть в будущем")
    private LocalDateTime eventDate;

    // --- ИЗМЕНЕНИЕ: Заменяем location на cityId ---
    @NotNull(message = "ID города не может быть пустым")
    private Long cityId;
    // ------------------------------------------

    private String imageUrl;

    @NotNull(message = "ID категории не может быть пустым")
    private Long categoryId;

    @NotEmpty(message = "Список переводов не может быть пустым")
    @Size(min = 1, message = "Должен быть хотя бы один перевод (немецкий)")
    private List<TranslationDto> translations;

    // --- Геттеры и сеттеры обновлены ---
    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public List<TranslationDto> getTranslations() { return translations; }
    public void setTranslations(List<TranslationDto> translations) { this.translations = translations; }
}
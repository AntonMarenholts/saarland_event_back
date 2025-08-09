package de.saarland.events.dto;

import de.saarland.events.model.EStatus;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDto {
    private Long id;
    private LocalDateTime eventDate;
    // --- ИЗМЕНЕНИЕ: Заменяем location на city ---
    private CityDto city;
    // ---------------------------------------
    private String imageUrl;
    private CategoryDto category;
    private List<TranslationDto> translations;
    private EStatus status;

    // --- Геттеры и сеттеры обновлены ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    public CityDto getCity() { return city; }
    public void setCity(CityDto city) { this.city = city; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public CategoryDto getCategory() { return category; }
    public void setCategory(CategoryDto category) { this.category = category; }
    public List<TranslationDto> getTranslations() { return translations; }
    public void setTranslations(List<TranslationDto> translations) { this.translations = translations; }
    public EStatus getStatus() { return status; }
    public void setStatus(EStatus status) { this.status = status; }
}
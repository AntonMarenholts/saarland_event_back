package de.saarland.events.mapper;

import de.saarland.events.dto.EventRequestDto;
import de.saarland.events.dto.EventResponseDto;
import de.saarland.events.dto.TranslationDto;
import de.saarland.events.model.Event;
import de.saarland.events.model.Translation;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final CityMapper cityMapper;

    public EventMapper(CategoryMapper categoryMapper, CityMapper cityMapper) {
        this.categoryMapper = categoryMapper;
        this.cityMapper = cityMapper;
    }

    public EventResponseDto toResponseDto(Event event) {
        if (event == null) {
            return null;
        }

        EventResponseDto dto = new EventResponseDto();
        dto.setId(event.getId());
        dto.setEventDate(event.getEventDate());
        dto.setImageUrl(event.getImageUrl());
        dto.setCity(cityMapper.toDto(event.getCity()));
        dto.setCategory(categoryMapper.toDto(event.getCategory()));
        dto.setTranslations(event.getTranslations().stream()
                .map(this::toTranslationDto)
                .collect(Collectors.toList()));
        dto.setStatus(event.getStatus());

        return dto;
    }

    public Event toEntity(EventRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Event event = new Event();
        event.setEventDate(dto.getEventDate());
        event.setImageUrl(dto.getImageUrl());
        // City и Category будут установлены в сервисе, здесь их не трогаем
        event.setTranslations(dto.getTranslations().stream()
                .map(this::toTranslationEntity)
                .collect(Collectors.toList()));

        return event;
    }

    private TranslationDto toTranslationDto(Translation translation) {
        if (translation == null) return null;
        TranslationDto dto = new TranslationDto();
        dto.setLocale(translation.getLocale());
        dto.setName(translation.getName());
        dto.setDescription(translation.getDescription());
        return dto;
    }

    private Translation toTranslationEntity(TranslationDto dto) {
        if (dto == null) return null;
        Translation translation = new Translation();
        translation.setLocale(dto.getLocale());
        translation.setName(dto.getName());
        translation.setDescription(dto.getDescription());
        return translation;
    }
}
package de.saarland.events.mapper;

import de.saarland.events.dto.CityDto;
import de.saarland.events.model.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDto toDto(City city) {
        if (city == null) {
            return null;
        }
        CityDto dto = new CityDto();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setLatitude(city.getLatitude());
        dto.setLongitude(city.getLongitude());
        return dto;
    }

    public City toEntity(CityDto dto) {
        if (dto == null) {
            return null;
        }
        City city = new City(dto.getName());
        // ▼▼▼ ДОБАВЛЯЕМ КООРДИНАТЫ ▼▼▼
        city.setLatitude(dto.getLatitude());
        city.setLongitude(dto.getLongitude());
        // ▲▲▲ КОНЕЦ ▲▲▲
        return city;
    }
}
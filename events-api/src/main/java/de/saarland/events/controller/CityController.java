// src/main/java/de/saarland/events/controller/CityController.java

package de.saarland.events.controller;

import de.saarland.events.dto.CityDto;
import de.saarland.events.mapper.CityMapper;
import de.saarland.events.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities") // Этот контроллер будет отвечать за путь /api/cities
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping // Этот метод будет обрабатывать GET-запросы
    public ResponseEntity<List<CityDto>> getAllCities() {
        // Получаем все города из сервиса
        List<CityDto> cities = cityService.getAllCities().stream()
                .map(cityMapper::toDto) // Превращаем каждую сущность City в CityDto
                .collect(Collectors.toList());
        // Отправляем список на фронтенд со статусом 200 OK
        return ResponseEntity.ok(cities);
    }
}
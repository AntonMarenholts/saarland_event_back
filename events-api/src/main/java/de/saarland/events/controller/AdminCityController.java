package de.saarland.events.controller;

import de.saarland.events.dto.CityDto;
import de.saarland.events.mapper.CityMapper;
import de.saarland.events.model.City;
import de.saarland.events.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/cities")
public class AdminCityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    public AdminCityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @PostMapping
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto cityDto) {
        City cityToCreate = cityMapper.toEntity(cityDto);
        City savedCity = cityService.saveCity(cityToCreate);
        return new ResponseEntity<>(cityMapper.toDto(savedCity), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
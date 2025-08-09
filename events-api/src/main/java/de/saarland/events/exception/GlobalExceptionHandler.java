package de.saarland.events.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice // Этот класс будет "ловить" исключения со всех контроллеров
public class GlobalExceptionHandler {



    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> response = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = Map.of("error", ex.getMessage());
        // 400 Bad Request - это более подходящий статус для такой ошибки
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Проверяем, содержит ли сообщение ошибки упоминание нашего ограничения уникальности
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            Map<String, String> response = Map.of("error", "Объект с таким именем уже существует.");
            // 409 Conflict - идеальный статус для такой ситуации
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        // Если это другая ошибка целостности, возвращаем стандартный 400 Bad Request
        Map<String, String> response = Map.of("error", "Ошибка целостности данных.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
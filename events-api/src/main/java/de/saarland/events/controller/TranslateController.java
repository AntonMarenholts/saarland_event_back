package de.saarland.events.controller;

import de.saarland.events.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/translate")
public class TranslateController {

    private final TranslationService translationService;

    public TranslateController(TranslationService translationService) {
        this.translationService = translationService;
    }

    // Создаем DTO прямо внутри класса для простоты
    // Он будет описывать JSON, который мы ожидаем: {"text": "...", "targetLang": "..."}
    public static class TranslateRequest {
        private String text;
        private String targetLang;
        // Геттеры и сеттеры обязательны для десериализации
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getTargetLang() { return targetLang; }
        public void setTargetLang(String targetLang) { this.targetLang = targetLang; }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> performTranslate(@RequestBody TranslateRequest request) {
        String translatedText = translationService.translate(request.getText(), request.getTargetLang());
        // Возвращаем ответ в формате {"translatedText": "..."}
        Map<String, String> response = Map.of("translatedText", translatedText);
        return ResponseEntity.ok(response);
    }
}
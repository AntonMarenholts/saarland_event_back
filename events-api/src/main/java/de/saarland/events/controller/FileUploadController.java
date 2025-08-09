package de.saarland.events.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final WebClient webClient;
    private final String supabaseUrl;
    private final String serviceKey;

    public FileUploadController(WebClient.Builder webClientBuilder,
                                @Value("${supabase.url}") String supabaseUrl,
                                @Value("${supabase.service_key}") String supabaseServiceKey) {
        this.supabaseUrl = supabaseUrl;
        this.serviceKey = supabaseServiceKey;
        this.webClient = webClientBuilder.baseUrl(this.supabaseUrl).build();
    }

    @PostMapping("/image")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Файл не должен быть пустым"));
        }

        String bucketName = "event-images";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        // Мы формируем путь для API-запроса
        String uploadPath = "/storage/v1/object/" + bucketName + "/" + fileName;

        try {
            // Выполняем прямой HTTP-запрос к API Supabase Storage
            webClient.post()
                    .uri(uploadPath)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceKey)
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .bodyValue(file.getBytes())
                    .retrieve() // Выполнить запрос
                    .toBodilessEntity() // Нам не нужен ответ, только статус
                    .block(); // Делаем запрос синхронным (ждем выполнения)

            // Формируем публичный URL, как и раньше
            String publicUrl = supabaseUrl + uploadPath;

            return ResponseEntity.ok(Map.of("imageUrl", publicUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Ошибка при загрузке файла: " + e.getMessage()));
        }
    }
}
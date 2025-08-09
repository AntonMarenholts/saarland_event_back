package de.saarland.events.dto;

import java.time.LocalDateTime;

public class ReviewResponseDto {

    private Long id;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private String username; // Имя пользователя, оставившего отзыв
    private Long userId;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
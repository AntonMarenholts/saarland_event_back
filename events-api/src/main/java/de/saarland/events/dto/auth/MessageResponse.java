// src/main/java/de/saarland/events/dto/auth/MessageResponse.java
package de.saarland.events.dto.auth;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
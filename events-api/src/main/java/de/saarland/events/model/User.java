// src/main/java/de/saarland/events/model/User.java

package de.saarland.events.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet; // <-- Добавить импорт
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> favoriteEvents = new HashSet<>();

    public User() {}

    public User(String username, String email, String password, ERole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public ERole getRole() { return role; }
    public void setRole(ERole role) { this.role = role; }
    public Set<Event> getFavoriteEvents() {
        return favoriteEvents;
    }

    public void setFavoriteEvents(Set<Event> favoriteEvents) {
        this.favoriteEvents = favoriteEvents;
    }
}
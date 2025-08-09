// src/main/java/de/saarland/events/AdminInitializer.java

package de.saarland.events;

import de.saarland.events.model.ERole;
import de.saarland.events.model.User;
import de.saarland.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("passwordEncoder")
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверяем, существует ли пользователь с ролью ADMIN
        if (!userRepository.existsByUsername(adminUsername)) {
            // Если нет, создаем его
            User adminUser = new User(
                    adminUsername,
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    ERole.ROLE_ADMIN
            );
            userRepository.save(adminUser);
            System.out.println(">>>> Created default ADMIN user <<<<");
        }
    }
}
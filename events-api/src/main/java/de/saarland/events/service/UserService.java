package de.saarland.events.service;

import de.saarland.events.model.User;
import de.saarland.events.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Пользователь с ID " + userId + " не найден.");
        }
        // ВАЖНО: В реальном приложении здесь должна быть более сложная логика
        // по очистке связанных данных (событий, напоминаний и т.д.)
        userRepository.deleteById(userId);
    }
}
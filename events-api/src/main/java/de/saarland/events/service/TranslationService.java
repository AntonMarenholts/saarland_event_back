package de.saarland.events.service;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final Translator translator;

    public TranslationService(@Value("${DEEPL_AUTH_KEY}") String deeplAuthKey) {
        this.translator = new Translator(deeplAuthKey);
    }

    public String translate(String text, String targetLang) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        try {
            // ▼▼▼ ИЗМЕНЕНИЕ ЗДЕСЬ ▼▼▼
            // Явно указываем исходный язык как немецкий ("DE")
            TextResult result = translator.translateText(text, "DE", targetLang);
            return result.getText();
        } catch (DeepLException | InterruptedException e) {
            System.err.println("Ошибка при переводе: " + e.getMessage());
            return "Translation Error";
        }
    }
}
package de.saarland.events.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import de.saarland.events.model.Event;
import de.saarland.events.model.User;
// 1. Импортируем логгер
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    // 2. Создаем экземпляр логгера
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final SendGrid sendGrid;

    @Value("${app.email.from}") // Используем свойство из application.properties
    private String fromEmail;

    public EmailService(@Value("${SENDGRID_API_KEY}") String sendGridApiKey) {
        this.sendGrid = new SendGrid(sendGridApiKey);
    }

    public void sendReminderEmail(User user, Event event) {
        Email to = new Email(user.getEmail());
        String subject = "Напоминание о событии: " + event.getTranslations().getFirst().getName();
        String textContent = String.format(
                "Привет, %s!\n\nНапоминаем, что скоро начнется событие, которое вы сохранили: '%s'.\nОно состоится %s.\n\n" +
                        "С наилучшими пожеланиями, команда Афиши Саарланда!",
                user.getUsername(),
                event.getTranslations().getFirst().getName(),
                event.getEventDate().toString()
        );
        Content content = new Content("text/plain", textContent);
        Mail mail = new Mail(new Email(fromEmail), subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            // 3. Используем логгер для вывода информации
            logger.info("Email reminder sent to {}. Status code: {}", user.getEmail(), response.getStatusCode());

        } catch (IOException ex) {
            // 4. Логируем ошибку и пробрасываем ее дальше
            logger.error("Error sending email to {}: {}", user.getEmail(), ex.getMessage());
            throw new RuntimeException("Failed to send email", ex);
        }
    }
}
package com.example.demo.services.email;

import com.example.demo.configurations.SendGridConfigurationProperties;
import com.example.demo.utils.DynamicTemplatePersonalization;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailDispatcher {
    private final SendGrid sendGrid;
    private final Email fromEmail;
    private final SendGridConfigurationProperties sendGridConfigurationProperties;
    @Value("${spring.application.base-url}")
    private String baseUrl;
    @Value("${app.sendgrid.template.reset-password-email}")
    private String resetPasswordEmailTemplateId;

    private static final String EMAIL_ENDPOINT = "mail/send";

    public void dispatchEmail(String emailId, String subject, String body) throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        sendRequest(mail);
    }

    public void dispatchResetPasswordEmail(String emailId, UUID token) throws IOException {
        Email toEmail = new Email(emailId);
        String link = baseUrl + "/api/v1/authentication/reset-password?token=" + token;
        DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.add("link", link);
        personalization.addTo(toEmail);
        Mail mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setTemplateId(resetPasswordEmailTemplateId);
        mail.addPersonalization(personalization);
        sendRequest(mail);
    }

    private void sendRequest(Mail mail) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(EMAIL_ENDPOINT);
        request.setBody(mail.build());
        sendGrid.api(request);
    }
}

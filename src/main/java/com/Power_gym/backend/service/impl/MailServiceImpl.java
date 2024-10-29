package com.Power_gym.backend.service.impl;


import com.Power_gym.backend.service.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendEmailAsync(String recipient, String subject, String body) {
        sendEmail(recipient, subject, body);
    }

    @Override
    public void sendEmail(String recipient, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true); // Set 'true' to indicate HTML content

            mailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", recipient);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}

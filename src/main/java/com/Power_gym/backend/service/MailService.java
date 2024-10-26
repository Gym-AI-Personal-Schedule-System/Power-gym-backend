package com.Power_gym.backend.service;

public interface MailService {
    void sendEmail(String recipient, String subject, String body);
    void sendEmailAsync(String recipient, String subject, String body);
}

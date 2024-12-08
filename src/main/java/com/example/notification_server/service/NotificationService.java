package com.example.notification_server.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.notification_server.domain.Contract;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(Contract contract) {
        // 이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contract.getUser().getEmail());
        message.setSubject("Contract Expiry Notification");
        message.setText(String.format("Your contract '%s' will expire on %s.",
                contract.getTitle(), contract.getExpiryDate()));

        // 이메일 전송
        mailSender.send(message);

        System.out.println("Email sent to: " + contract.getUser().getEmail());
    }
}



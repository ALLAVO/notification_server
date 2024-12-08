package com.example.notification_server.controller;

import com.example.notification_server.repository.ContractRepository;
import com.example.notification_server.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.notification_server.domain.Contract;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ContractRepository contractRepository;

    public NotificationController(NotificationService notificationService, ContractRepository contractRepository) {
        this.notificationService = notificationService;
        this.contractRepository = contractRepository;
    }

    @PostMapping("/send")
    public String sendNotifications() {
        List<Contract> contracts = contractRepository.findByExpiryDate(LocalDate.now().plusDays(1));

        for (Contract contract : contracts) {
            notificationService.sendNotification(contract);
        }

        return "Notifications sent successfully.";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @PostMapping("/send/{contractId}")
    public String sendNotificationForContract(@PathVariable Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found for ID: " + contractId));
        notificationService.sendNotification(contract);
        return "Notification sent successfully for contract ID: " + contractId;
    }
}




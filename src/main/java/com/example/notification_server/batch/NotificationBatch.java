package com.example.notification_server.batch;

import com.example.notification_server.repository.ContractRepository;
import com.example.notification_server.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.notification_server.domain.Contract;


import java.time.LocalDate;
import java.util.List;

public class NotificationBatch {
    private final ContractRepository contractRepository;
    private final NotificationService notificationService;

    public NotificationBatch(ContractRepository contractRepository, NotificationService notificationService) {
        this.contractRepository = contractRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시에 실행
    public void sendNotifications() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Contract> contracts = contractRepository.findByExpiryDate(tomorrow);

        for (Contract contract : contracts) {
            notificationService.sendNotification(contract);
        }
    }
}

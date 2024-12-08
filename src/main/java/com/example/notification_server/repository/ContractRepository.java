package com.example.notification_server.repository;

import com.example.notification_server.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    /**
     * Find contracts by their expiry date.
     *
     * @param date The expiry date to filter contracts.
     * @return A list of contracts with the specified expiry date.
     */
    List<Contract> findByExpiryDate(LocalDate date);
}
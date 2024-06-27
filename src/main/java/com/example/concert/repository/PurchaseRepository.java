package com.example.concert.repository;

import com.example.concert.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByTicketIdAndPurchaseStatusIn(Long ticketId, List<String> purchaseStatuses);
}

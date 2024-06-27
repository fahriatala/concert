package com.example.concert.service.purchase;

import com.example.concert.config.exception.AppException;
import com.example.concert.config.exception.DataNotFoundException;
import com.example.concert.model.entity.Purchase;
import com.example.concert.model.entity.Ticket;
import com.example.concert.model.enums.PurchaseStatus;
import com.example.concert.repository.PurchaseRepository;
import com.example.concert.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public void purchaseTicket(Long ticketId) {
        // Lock the ticket for update to prevent concurrent modifications
        Ticket ticket = ticketRepository.findByIdWithLock(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));

        if (ticket.getTicketRemaining() < 1) {
            throw new AppException("Ticket is unavailable, check the availability on the concert page");
        }

        // Validate purchase time
        LocalDateTime now = LocalDateTime.now();
        if (ticket.getTicketPurchaseStart() != null && ticket.getTicketPurchaseEnd() != null &&
                (now.isBefore(ticket.getTicketPurchaseStart()) || now.isAfter(ticket.getTicketPurchaseEnd()))
        ) {
                throw new AppException("Ticket purchase time is outside allowed range");

        }

        Purchase purchase = new Purchase();
        purchase.setTicketId(ticketId);
        purchase.setPurchaseStatus(PurchaseStatus.BOOKED.getValue());
        purchase.setCreatedAt(now);
        purchase.setUpdatedAt(now);
        purchaseRepository.save(purchase);

        // Update ticket_remaining field
        ticket.setTicketRemaining(ticket.getTicketRemaining() - 1);
        ticketRepository.save(ticket);

    }
}

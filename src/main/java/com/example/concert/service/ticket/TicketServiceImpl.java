package com.example.concert.service.ticket;

import com.example.concert.config.exception.AppException;
import com.example.concert.config.exception.DataNotFoundException;
import com.example.concert.model.entity.Ticket;
import com.example.concert.model.request.TicketRequest;
import com.example.concert.repository.ConcertRepository;
import com.example.concert.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public void addTicket(TicketRequest request) {
        if (!concertRepository.existsById(request.getConcertId())) {
            throw new DataNotFoundException("Concert ID is not found");
        }

        if (request.getTicketPurchaseStart().isBefore(LocalDateTime.now())) {
            throw new AppException("ticket purchase date must be in the future");
        }

        if (request.getTicketPurchaseEnd().isBefore(request.getTicketPurchaseStart())) {
            throw new AppException("Ticket purchase end date must be after purchase start date");
        }

        Ticket ticket = new Ticket();
        ticket.setConcertId(request.getConcertId());
        ticket.setTicketClass(request.getTicketClass());
        ticket.setTicketTotal(request.getTicketTotal());
        ticket.setTicketAmount(request.getTicketAmount());
        ticket.setTicketPurchaseStart(request.getTicketPurchaseStart());
        ticket.setTicketPurchaseEnd(request.getTicketPurchaseEnd());
        ticketRepository.save(ticket);

    }
}

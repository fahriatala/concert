package com.example.concert.service.concert;

import com.example.concert.model.request.ConcertRequest;
import com.example.concert.model.response.TicketConcertResponse;

import java.util.List;

public interface ConcertService {
    void addConcert(ConcertRequest request);
    TicketConcertResponse getConcertWithTickets(Long concertId);
    List<TicketConcertResponse> getAllConcertsWithTickets(String name);
}

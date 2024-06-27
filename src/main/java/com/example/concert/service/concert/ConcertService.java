package com.example.concert.service.concert;

import com.example.concert.model.request.ConcertRequest;
import com.example.concert.model.response.TicketConcertResponse;

public interface ConcertService {
    void addConcert(ConcertRequest request);
    TicketConcertResponse getConcertWithTickets(Long concertId);
}

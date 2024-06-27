package com.example.concert.service.concert;

import com.example.concert.config.exception.AppException;
import com.example.concert.config.exception.DataNotFoundException;
import com.example.concert.model.entity.Concert;
import com.example.concert.model.request.ConcertRequest;
import com.example.concert.model.response.TicketConcertResponse;
import com.example.concert.model.response.TicketResponse;
import com.example.concert.repository.ConcertRepository;
import com.example.concert.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void addConcert(ConcertRequest request) {
        if (request.getConcertDate().isBefore(LocalDateTime.now())) {
            throw new AppException("Concert date must be in the future");
        }

        Concert concert = new Concert();
        concert.setConcertName(request.getConcertName());
        concert.setConcertDescription(request.getConcertDescription());
        concert.setConcertLocation(request.getConcertLocation());
        concert.setConcertDate(request.getConcertDate());
        concertRepository.save(concert);
    }

    @Override
    public TicketConcertResponse getConcertWithTickets(Long concertId) {
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new DataNotFoundException("Concert not found"));

        List<TicketResponse> tickets = ticketRepository.findByConcertId(concertId).stream()
                .map(ticket -> {
                    TicketResponse ticketResponse = new TicketResponse();
                    ticketResponse.setId(ticket.getId());
                    ticketResponse.setTicketClass(ticket.getTicketClass());
                    ticketResponse.setTicketTotal(ticket.getTicketTotal());
                    ticketResponse.setTicketAmount(ticket.getTicketAmount());
                    return ticketResponse;
                })
                .collect(Collectors.toList());

        TicketConcertResponse response = new TicketConcertResponse();
        response.setConcertId(concert.getId());
        response.setConcertName(concert.getConcertName());
        response.setConcertDescription(concert.getConcertDescription());
        response.setConcertLocation(concert.getConcertLocation());
        response.setConcertDate(concert.getConcertDate());
        response.setTickets(tickets);

        return response;
    }

    @Override
    public List<TicketConcertResponse> getAllConcertsWithTickets(String name) {
        List<Concert> listOfConcert;
        if (name != null) {
            listOfConcert = concertRepository.findByConcertNameContaining(name);
        } else {
            listOfConcert = concertRepository.findAll();
        }

        return listOfConcert.stream()
                .map(concert -> {
                    List<TicketResponse> tickets = ticketRepository.findByConcertId(concert.getId()).stream()
                            .map(ticket -> {
                                TicketResponse ticketResponse = new TicketResponse();
                                ticketResponse.setId(ticket.getId());
                                ticketResponse.setTicketClass(ticket.getTicketClass());
                                ticketResponse.setTicketTotal(ticket.getTicketTotal());
                                ticketResponse.setTicketAmount(ticket.getTicketAmount());
                                return ticketResponse;
                            })
                            .collect(Collectors.toList());

                    TicketConcertResponse response = new TicketConcertResponse();
                    response.setConcertId(concert.getId());
                    response.setConcertName(concert.getConcertName());
                    response.setConcertDescription(concert.getConcertDescription());
                    response.setConcertLocation(concert.getConcertLocation());
                    response.setConcertDate(concert.getConcertDate());
                    response.setTickets(tickets);

                    return response;
                })
                .collect(Collectors.toList());
    }
}

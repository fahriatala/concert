package com.example.concert.controller;

import com.example.concert.config.base.AbstractResponseHandler;
import com.example.concert.config.base.ResultResponse;
import com.example.concert.model.request.TicketRequest;
import com.example.concert.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractResponseHandler {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<ResultResponse<Boolean>> addTicket(@RequestBody TicketRequest request) {
        ticketService.addTicket(request);

        return generateResponse(
                true,
                HttpStatus.CREATED,
                "Success Create Ticket"
        );
    }
}

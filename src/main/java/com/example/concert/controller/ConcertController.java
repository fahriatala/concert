package com.example.concert.controller;

import com.example.concert.config.base.AbstractResponseHandler;
import com.example.concert.config.base.ResultResponse;
import com.example.concert.model.request.ConcertRequest;
import com.example.concert.model.response.TicketConcertResponse;
import com.example.concert.service.concert.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController extends AbstractResponseHandler {
    @Autowired
    private ConcertService concertService;

    @PostMapping
    public ResponseEntity<ResultResponse<Boolean>> addConcert(@RequestBody ConcertRequest request) {
        concertService.addConcert(request);

        return generateResponse(
                true,
                HttpStatus.CREATED,
                "Success Create Job"
        );
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ResultResponse<TicketConcertResponse>> getConcertWithTickets(@PathVariable Long concertId) {
        TicketConcertResponse response = concertService.getConcertWithTickets(concertId);

        return generateResponse(response, HttpStatus.OK, null);
    }

    @GetMapping
    public ResponseEntity<ResultResponse<List<TicketConcertResponse>>> getAllConcertsWithTickets() {
        List<TicketConcertResponse> response = concertService.getAllConcertsWithTickets();
        return generateResponse(response, HttpStatus.OK, null);
    }
}

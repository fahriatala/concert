package com.example.concert.controller;

import com.example.concert.config.base.AbstractResponseHandler;
import com.example.concert.config.base.ResultResponse;
import com.example.concert.model.request.ConcertRequest;
import com.example.concert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

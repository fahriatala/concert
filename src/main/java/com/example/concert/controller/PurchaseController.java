package com.example.concert.controller;

import com.example.concert.config.base.AbstractResponseHandler;
import com.example.concert.config.base.ResultResponse;
import com.example.concert.config.ratelimiter.RateLimited;
import com.example.concert.model.request.PurchaseRequest;
import com.example.concert.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController extends AbstractResponseHandler {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    @RateLimited()
    public ResponseEntity<ResultResponse<Boolean>> purchaseTicket(@RequestBody PurchaseRequest request) {
        purchaseService.purchaseTicket(request.getTicketId());

        return generateResponse(
                true,
                HttpStatus.CREATED,
                "Success Purchasing Ticket"
        );
    }
}

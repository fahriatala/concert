package com.example.concert.model.response;

import lombok.Data;

@Data
public class TicketResponse {
    private Long id;
    private String ticketClass;
    private Integer ticketTotal;
    private Double ticketAmount;
    private String ticketStatus;
}

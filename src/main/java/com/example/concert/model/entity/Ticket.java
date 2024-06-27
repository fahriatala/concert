package com.example.concert.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concert_id")
    private Long concertId;

    @Column(name = "ticket_class")
    private String ticketClass;

    @Column(name = "ticket_total")
    private Integer ticketTotal;

    @Column(name = "ticket_remaining")
    private Integer ticketRemaining;

    @Column(name = "ticket_amount")
    private Double ticketAmount;

    @Column(name = "ticket_purchase_start")
    private LocalDateTime ticketPurchaseStart;

    @Column(name = "ticket_purchase_end")
    private LocalDateTime ticketPurchaseEnd;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

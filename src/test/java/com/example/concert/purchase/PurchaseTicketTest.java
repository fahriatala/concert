package com.example.concert.purchase;

import com.example.concert.config.exception.AppException;
import com.example.concert.model.entity.Purchase;
import com.example.concert.model.entity.Ticket;
import com.example.concert.repository.PurchaseRepository;
import com.example.concert.repository.TicketRepository;
import com.example.concert.service.purchase.PurchaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PurchaseTicketTest {
    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Captor
    private ArgumentCaptor<Ticket> ticketArgumentCaptor;

    @Captor
    private ArgumentCaptor<Purchase> purchaseArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseTicket_success() {
        Ticket ticket = new Ticket();
        ticket.setTicketRemaining(10);
        ticket.setTicketPurchaseStart(LocalDateTime.now().minusDays(1));
        ticket.setTicketPurchaseEnd(LocalDateTime.now().plusDays(1));

        when(ticketRepository.findByIdWithLock(1L)).thenReturn(Optional.of(ticket));

        purchaseService.purchaseTicket(1L);

        verify(ticketRepository, times(1)).save(ticket);
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
        Assertions.assertEquals(9, ticket.getTicketRemaining());
    }

    @Test
    void testPurchaseTicket_noTicketsAvailable() {
        Ticket ticket = new Ticket();
        ticket.setTicketRemaining(0);

        when(ticketRepository.findByIdWithLock(1L)).thenReturn(Optional.of(ticket));

        Exception exception = assertThrows(AppException.class, () -> {
            purchaseService.purchaseTicket(1L);
        });

        Assertions.assertEquals(
                "Ticket is unavailable, check the availability on the concert page", exception.getMessage()
        );
    }
}

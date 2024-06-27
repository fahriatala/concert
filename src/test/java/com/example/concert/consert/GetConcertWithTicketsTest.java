package com.example.concert.consert;

import com.example.concert.config.exception.DataNotFoundException;
import com.example.concert.model.entity.Concert;
import com.example.concert.model.entity.Ticket;
import com.example.concert.model.response.TicketConcertResponse;
import com.example.concert.repository.ConcertRepository;
import com.example.concert.repository.TicketRepository;
import com.example.concert.service.concert.ConcertServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetConcertWithTicketsTest {
    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private ConcertServiceImpl concertService;

    @Captor
    private ArgumentCaptor<Concert> concertArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getConcertWithTickets_shouldReturnConcertWithTickets_whenConcertExists() {
        Long concertId = 1L;
        Concert concert = new Concert();
        concert.setId(concertId);
        concert.setConcertName("Test Concert");
        concert.setConcertDescription("Test Description");
        concert.setConcertLocation("Test Location");
        concert.setConcertDate(LocalDateTime.now().plusDays(1));

        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setTicketClass("VIP");
        ticket1.setTicketTotal(100);
        ticket1.setTicketAmount(150.0);
        ticket1.setConcertId(concertId);

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setTicketClass("Regular");
        ticket2.setTicketTotal(200);
        ticket2.setTicketAmount(50.0);
        ticket2.setConcertId(concertId);

        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));
        when(ticketRepository.findByConcertId(concertId)).thenReturn(Arrays.asList(ticket1, ticket2));

        TicketConcertResponse response = concertService.getConcertWithTickets(concertId);

        Assertions.assertEquals(concertId, response.getConcertId());
        Assertions.assertEquals("Test Concert", response.getConcertName());
        Assertions.assertEquals(2, response.getTickets().size());
        verify(concertRepository, times(1)).findById(concertId);
        verify(ticketRepository, times(1)).findByConcertId(concertId);
    }

    @Test
    void getConcertWithTickets_shouldThrowException_whenConcertDoesNotExist() {
        Long concertId = 1L;
        when(concertRepository.findById(concertId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> concertService.getConcertWithTickets(concertId));
        verify(concertRepository, times(1)).findById(concertId);
        verify(ticketRepository, never()).findByConcertId(concertId);
    }
}

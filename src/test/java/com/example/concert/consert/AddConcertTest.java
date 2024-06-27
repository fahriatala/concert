package com.example.concert.consert;

import com.example.concert.config.exception.AppException;
import com.example.concert.model.entity.Concert;
import com.example.concert.model.request.ConcertRequest;
import com.example.concert.repository.ConcertRepository;
import com.example.concert.service.concert.ConcertServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddConcertTest {
    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertServiceImpl concertService;

    @Captor
    private ArgumentCaptor<Concert> concertArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addConcert_shouldSaveConcert_whenValidRequest() {
        when(concertRepository.save(concertArgumentCaptor.capture()))
                .thenAnswer(invocation -> {
                    Concert savedConcert = invocation.getArgument(0);
                    savedConcert.setId(1L);
                    return savedConcert;
                });

        ConcertRequest concertRequest = new ConcertRequest();
        concertRequest.setConcertName("Test Concert");
        concertRequest.setConcertDescription("Test Description");
        concertRequest.setConcertLocation("Test Location");
        concertRequest.setConcertDate(LocalDateTime.now().plusDays(1));
        concertService.addConcert(concertRequest);

        verify(concertRepository).save(concertArgumentCaptor.capture());
        Concert capturedConcert = concertArgumentCaptor.getValue();
        Assertions.assertNotNull(capturedConcert.getConcertDate());
    }

    @Test
    void addConcert_shouldThrowException_whenConcertDateInPast() {
        ConcertRequest concertRequest = new ConcertRequest();
        concertRequest.setConcertName("Test Concert");
        concertRequest.setConcertDescription("Test Description");
        concertRequest.setConcertLocation("Test Location");
        concertRequest.setConcertDate(LocalDateTime.now().minusDays(1));

        assertThrows(AppException.class, () -> concertService.addConcert(concertRequest));
    }
}

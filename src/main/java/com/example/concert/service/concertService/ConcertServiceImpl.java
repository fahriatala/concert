package com.example.concert.service.concertService;

import com.example.concert.config.exception.AppException;
import com.example.concert.model.entity.Concert;
import com.example.concert.model.request.ConcertRequest;
import com.example.concert.repository.ConcertRepository;
import com.example.concert.service.concertService.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

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
}

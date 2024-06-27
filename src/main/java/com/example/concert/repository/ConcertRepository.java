package com.example.concert.repository;

import com.example.concert.model.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByConcertNameContaining(@Param("concertName") String concertName);
}

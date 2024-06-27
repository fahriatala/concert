package com.example.concert.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "concert")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concert_name")
    private String concertName;

    @Column(name = "concert_description")
    private String concertDescription;

    @Column(name = "concert_location")
    private String concertLocation;

    @Column(name = "concert_date")
    private LocalDateTime concertDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

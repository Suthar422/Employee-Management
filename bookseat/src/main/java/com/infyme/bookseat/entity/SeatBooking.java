package com.infyme.bookseat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "seat_id")
    private String seatId;


    private LocalDate bookingDate;

    private LocalDateTime bookingAt;

    private String status;
}

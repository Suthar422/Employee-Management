package com.bookseat.notification.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SeatBooking {

    private Long bookingId;

    private Long userId;

    private String seatId;

    private LocalDate bookingDate;

    private LocalDateTime bookingAt;

    private String status;
}

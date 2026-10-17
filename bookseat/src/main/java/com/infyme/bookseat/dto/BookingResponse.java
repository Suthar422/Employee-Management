package com.infyme.bookseat.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookingResponse {

    private Long bookingId;

    private Long userId;

    private String seatId;

    private String message;

}

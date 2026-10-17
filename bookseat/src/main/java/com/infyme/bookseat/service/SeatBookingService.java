package com.infyme.bookseat.service;

import com.infyme.bookseat.dto.BookingRequest;
import com.infyme.bookseat.dto.BookingResponse;


public interface SeatBookingService {

    public BookingResponse bookSeat(BookingRequest bookingRequest, Long userId);

    BookingResponse getBookingInfoForUser(Long userId);

    void deleteBookingInfoForUser(Long userId);

    void updateBookingInfoForUser(Long userId, String seatId);
}
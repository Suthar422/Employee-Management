package com.infyme.bookseat.repository;

import com.infyme.bookseat.dto.BookingResponse;
import com.infyme.bookseat.entity.SeatBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {


     Optional<SeatBooking> findByUserId(Long userId);


     void deleteByUserId(Long userId);
}

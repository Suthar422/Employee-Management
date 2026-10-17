package com.infyme.bookseat.controller;

import com.infyme.bookseat.dto.BookingRequest;
import com.infyme.bookseat.dto.BookingResponse;
import com.infyme.bookseat.service.SeatBookingService;
import com.infyme.bookseat.service.impl.CacheInspectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bookseat")
public class SeatBookingController {

    private final SeatBookingService seatBookingService;

    private final CacheInspectionService cacheInspectionService;

    public SeatBookingController(SeatBookingService seatBookingService, CacheInspectionService cacheInspectionService) {
        this.seatBookingService = seatBookingService;
        this.cacheInspectionService = cacheInspectionService;
    }

    //book seat
    @PostMapping(value = "/seat/{userId}")
    public ResponseEntity<BookingResponse> bookSeat(@RequestBody BookingRequest bookingRequest, @PathVariable Long userId) {
        log.info("Booking seat for user: {}", userId);
        return ResponseEntity.ok(seatBookingService.bookSeat(bookingRequest, userId));
    }


    //fetch booking info: want for users
    @GetMapping(value = "/{userId}")
    public ResponseEntity<BookingResponse> getBookingInfoForUser(@PathVariable Long userId) {
        log.info("Fetching booking info for user: {}", userId);
        return ResponseEntity.ok(seatBookingService.getBookingInfoForUser(userId));
    }


    @GetMapping("/cacheData")
    public void inspectCache() {
        cacheInspectionService.inspectCache("seatInfoCache");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteBookingInfoForUser(@PathVariable Long userId) {
        log.info("Deleting booking info for user: {}", userId);
        seatBookingService.deleteBookingInfoForUser(userId);
        return new ResponseEntity<>("Booking info deleted for user: " + userId, HttpStatus.OK);
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<String> updateBookingInfoForUser(@PathVariable Long userId, @RequestParam String seatId) {
        log.info("Updating booking info for user: {}", userId);
        seatBookingService.updateBookingInfoForUser(userId, seatId);
        return new ResponseEntity<>("Booking info updated for user: " + userId, HttpStatus.OK);
    }
}

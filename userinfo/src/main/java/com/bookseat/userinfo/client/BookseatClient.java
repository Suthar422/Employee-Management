package com.bookseat.userinfo.client;

import com.bookseat.userinfo.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-seat-service")
public interface BookseatClient {


    @GetMapping(value = "/bookseat/{userId}")
    public BookingResponse getBookingInfoById(@PathVariable Long userId);

    }

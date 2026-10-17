package com.infyme.bookseat.dto;

import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;


@Data
@Getter
public class BookingRequest {

    @NotNull
    private String seatId;

    @NotNull
    private LocalDate bookingDate;
}

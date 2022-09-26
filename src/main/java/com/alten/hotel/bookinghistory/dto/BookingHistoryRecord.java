package com.alten.hotel.bookinghistory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Used to answer and show to the user information
 * about the histories of a booking
 *
 * @author luis.bonfa
 */
@Getter
@Builder
public class BookingHistoryRecord {

    private UUID bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}

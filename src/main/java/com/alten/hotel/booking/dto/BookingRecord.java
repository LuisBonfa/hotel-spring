package com.alten.hotel.booking.dto;

import com.alten.hotel.common.enums.CommonStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Used to answer and show to the user information
 * about the bookings
 *
 * @author luis.bonfa
 */
@Getter
@Builder
public class BookingRecord {

    private UUID userId;
    private UUID bookingId;
    private String bookingCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    private CommonStatus status;
}

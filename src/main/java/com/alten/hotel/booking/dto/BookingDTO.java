package com.alten.hotel.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO used to UPDATE and INSERT
 * new bookings
 *
 * @author luis.bonfa
 */
@Getter
@Setter
@NoArgsConstructor
public class BookingDTO {

    private UUID userId;

    @NotNull(message = "Begin date can't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @NotNull(message = "End date can't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

}

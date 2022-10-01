package com.alten.hotel.booking.controller;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.service.BookingService;
import com.alten.hotel.common.controller.DefaultController;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Controller of bookings {@link BookingEntity}.
 * <p>
 * Almost all endpoints
 * have the response in {@link BookingRecord} type.
 *
 * @author luis.bonfa
 */
@CrossOrigin
@RestController
@RequestMapping("/booking")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingController extends DefaultController<BookingDTO, BookingRecord> {

    BookingService service;

    @GetMapping("/exists")
    public boolean evaluateExistence(@RequestParam String begin, @RequestParam String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Instant beginDate = LocalDate.parse(begin, formatter).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDate.parse(end, formatter).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
        return service.verifyBookingExistence(beginDate, endDate);
    }
}

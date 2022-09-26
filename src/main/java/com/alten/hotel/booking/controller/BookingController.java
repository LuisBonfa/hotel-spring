package com.alten.hotel.booking.controller;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.service.BookingService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class BookingController {

    BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/exists")
    public boolean evaluateExistence(@RequestParam String begin, @RequestParam String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Instant beginDate = LocalDate.parse(begin, formatter).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDate.parse(end, formatter).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);
        return bookingService.verifyBookingExistence(beginDate, endDate);
    }

    @PostMapping
    public ResponseEntity<?> save(@Validated @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.save(bookingDTO));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> delete(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.delete(bookingId));
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> update(@PathVariable String bookingId, @Validated @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.update(bookingId, bookingDTO));
    }
}

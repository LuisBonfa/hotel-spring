package com.senior.challenge.user.controller;

import com.senior.challenge.user.dto.BookingDTO;
import com.senior.challenge.user.dto.ProductsDTO;
import com.senior.challenge.user.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.save(bookingDTO));
    }

    @PutMapping("/{bookingId}/checkOut")
    public ResponseEntity<?> checkout(@RequestBody ProductsDTO productsDTO, @PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.checkOut(productsDTO, UUID.fromString(bookingId)));
    }

    @PutMapping("/{bookingId}/checkIn")
    public ResponseEntity<?> checkIn(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.checkIn(UUID.fromString(bookingId)));
    }

    @GetMapping
    public  ResponseEntity<?> findAll(){
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{bookingId}")
    public  ResponseEntity<?> findById(@PathVariable String bookingId){
        return ResponseEntity.ok(bookingService.findById(UUID.fromString(bookingId)));
    }

    @PutMapping("/{bookingId}")
    public  ResponseEntity<?> update(@RequestBody BookingDTO bookingDTO, @PathVariable String bookingId){
        return ResponseEntity.ok(bookingService.update(bookingDTO, UUID.fromString(bookingId)));
    }
}

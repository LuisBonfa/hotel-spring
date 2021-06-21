package com.senior.challenge.user.controller;

import com.senior.challenge.user.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    //Needs to be done
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(billService.findAll());
    }

    //Needs to be done
    @GetMapping("/{billId}")
    public ResponseEntity<?> findById(@PathVariable String billId){
        return ResponseEntity.ok(billService.findById(UUID.fromString(billId)));
    }

    //Needs to be done
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> findByBooking(@PathVariable String bookingId){
        return ResponseEntity.ok(billService.findByBooking(UUID.fromString(bookingId)));
    }
}

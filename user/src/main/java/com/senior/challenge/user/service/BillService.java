package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Bill;
import com.senior.challenge.user.entity.Booking;
import com.senior.challenge.user.enums.BillStatus;
import com.senior.challenge.user.enums.Prices;
import com.senior.challenge.user.repository.BillRepository;
import com.senior.challenge.user.repository.BookingRepository;
import com.senior.challenge.user.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BillService(BillRepository billRepository, BookingRepository bookingRepository) {
        this.billRepository = billRepository;
        this.bookingRepository = bookingRepository;
    }

    public Bill calculateBillAndSave(Booking booking, Map<String, Integer> products) {

        // Validar quantas diárias e quantas são em dias de semana e finais de semana
        // Validar

        try {
            var begin = Calendar.getInstance();
            var end = Calendar.getInstance();

            var bill = new Bill();

            var garagePrice = 0.0;
            var dailiesPrice = 0.0;

            begin.setTime(booking.getBegin());
            end.setTime(booking.getEnd());

            long time = booking.getEnd().getTime() - booking.getBegin().getTime();
            long days = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS);

            // Get the total amount of days and calculate the total price of the
            // hotel dailies based in pr
            for (var i = 0; i < days; i++) {
                if (Validator.verifyWeekend(begin)) {
                    dailiesPrice += Prices.ROOM_WEEKEND.getPrice();
                    garagePrice += (Boolean.TRUE.equals(booking.getGarage())) ? Prices.GARAGE_WEEKEND.getPrice() : 0;
                } else {
                    dailiesPrice += Prices.ROOM_WEEKDAY.getPrice();
                    garagePrice += (Boolean.TRUE.equals(booking.getGarage())) ? Prices.GARAGE_WEEKDAY.getPrice() : 0;
                }

                begin.add(Calendar.DAY_OF_WEEK, 1);
            }

            // Verify if the checkout was after the booking end
            // If it is, a daily price is added to the bill
            if (booking.getCheckOut().after(booking.getEnd())) {
                if (Validator.verifyWeekend(end))
                    dailiesPrice += Prices.ROOM_WEEKEND.getPrice();
                else
                    dailiesPrice += Prices.ROOM_WEEKDAY.getPrice();
            }

            Double productsPrice = getProductsPrice(products);

            bill.setBooking(booking);
            bill.setDailiesPrice(dailiesPrice);
            bill.setProductsPrice(productsPrice);
            bill.setGaragePrice(garagePrice);
            bill.setStatus(BillStatus.WAITING.getStatus());
            bill.setTotal(dailiesPrice + garagePrice + productsPrice);
            bill.setProducts(products);
            bill.setCreationDate();

            billRepository.save(bill);
            return bill;

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Checking out", pe);
        }
    }

    public List<Bill> findAll() {
        try {
            return billRepository.findAll();
        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Listing Bills", pe);
        }
    }

    public Bill findById(UUID billId) {
        try {
            Optional<Bill> bill = billRepository.findById(billId);
            if (bill.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill Not Found");

            return bill.get();

        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Listing Bills", pe);
        }
    }

    public List<Bill> findByBooking(UUID bookingId) {
        try {
            Optional<Booking> booking = bookingRepository.findById(bookingId);

            if (booking.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not Found");

            List<Bill> bills = billRepository.findByBooking(booking.get());

            if (bills.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not Found");

            return bills;
        } catch (PersistenceException pe) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading bill", pe);
        }
    }

    private Double getProductsPrice(Map<String, Integer> products) {
        final Double[] productsPrice = {0.0};
        if (!products.isEmpty()) {
            products.forEach((k, v) -> {
                var price = Prices.valueOf(k.toUpperCase());
                productsPrice[0] += price.getPrice() * (int) v;
            });
        }

        return productsPrice[0];
    }
}

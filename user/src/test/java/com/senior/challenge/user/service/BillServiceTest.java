package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.Bill;
import com.senior.challenge.user.entity.Booking;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.repository.BillRepository;
import com.senior.challenge.user.repository.BookingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BillServiceTest.class})
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    private void setUp() {
        billService = new BillService(billRepository, bookingRepository);
    }

    @Test
    void billCreationInCheckoutTest() {

        var checkIn = Calendar.getInstance();
        var checkOut = Calendar.getInstance();

        checkIn.set(2021, 5, 18, 13, 0);
        checkOut.set(2021, 5, 20, 15, 40);

        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        Booking booking = Booking.generateBookingForTesting(checkIn, checkOut, user);

        Map<String, Integer> products = new HashMap<>();
        products.put("coke", 2);
        products.put("peanuts", 2);
        products.put("water", 2);
        products.put("chocolate", 2);

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(351.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(products);
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(270.0);
        bill.setCreationDate();
        bill.setBooking(booking);
        bill.setStatus("waiting");

        Bill expectedBill = billService.calculateBillAndSave(booking, products);

        verify(billRepository, times(1)).save(any(Bill.class));
        Assertions.assertThat(expectedBill).isNotNull();
        Assertions.assertThat(expectedBill.getTotal()).isEqualTo(351.0);
    }

    @Test
    void billCreationInCheckoutWithFineTest() {
        var checkIn = Calendar.getInstance();
        var checkOut = Calendar.getInstance();

        // This is where the fine is detected
        // If the checkout is done after 4:30 PM he has to pay
        // one more hotel daily

        checkIn.set(2021, 5, 18, 13, 0);
        checkOut.set(2021, 5, 20, 16, 40);

        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        Booking booking = Booking.generateBookingForTesting(checkIn, checkOut, user);

        Map<String, Integer> products = new HashMap<>();
        products.put("coke", 2);
        products.put("peanuts", 2);
        products.put("water", 2);
        products.put("chocolate", 2);

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(501.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(products);
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(420.0);
        bill.setCreationDate();
        bill.setBooking(booking);
        bill.setStatus("waiting");

        Bill expectedBill = billService.calculateBillAndSave(booking, products);

        verify(billRepository, times(1)).save(any(Bill.class));
        Assertions.assertThat(expectedBill).isNotNull();
        Assertions.assertThat(expectedBill.getTotal()).isEqualTo(501);
    }

    @Test
    void findAllBills() {

        List<Bill> bills = new ArrayList<>();

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(501.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(new HashMap<>());
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(420.0);
        bill.setCreationDate();
        bill.setBooking(new Booking());
        bill.setStatus("waiting");

        Bill secondBill = new Bill();
        secondBill.setId(UUID.randomUUID());
        secondBill.setTotal(501.0);
        secondBill.setProductsPrice(46.0);
        secondBill.setProducts(new HashMap<>());
        secondBill.setGaragePrice(35.0);
        secondBill.setDailiesPrice(420.0);
        secondBill.setCreationDate();
        secondBill.setBooking(new Booking());
        secondBill.setStatus("waiting");

        bills.add(bill);
        bills.add(secondBill);

        when(billRepository.findAll()).thenReturn(bills);

        List<Bill> expectedBills = billService.findAll();

        Assertions.assertThat(expectedBills).isNotEmpty();
        Assertions.assertThat(expectedBills.size()).isEqualTo(2);
        Assertions.assertThat(expectedBills.get(1).getId()).isEqualTo(secondBill.getId());
    }

    @Test
    void findBillById() {
        Map<String, Integer> products = new HashMap<>();
        products.put("coke", 2);
        products.put("peanuts", 2);
        products.put("water", 2);
        products.put("chocolate", 2);

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(501.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(products);
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(420.0);
        bill.setCreationDate();
        bill.setBooking(new Booking());
        bill.setStatus("waiting");

        when(billRepository.findById(bill.getId())).thenReturn(Optional.of(bill));

        Bill expectedBill = billService.findById(bill.getId());
        verify(billRepository, times(1)).findById(bill.getId());
        Assertions.assertThat(expectedBill).isNotNull();
    }

    @Test
    void findBillByBookingId() {
        var checkIn = Calendar.getInstance();
        var checkOut = Calendar.getInstance();

        // This is where the fine is detected
        // If the checkout is done after 4:30 PM he has to pay
        // one more hotel daily

        checkIn.set(2021, 5, 18, 13, 0);
        checkOut.set(2021, 5, 20, 15, 40);

        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        Booking booking = Booking.generateBookingForTesting(checkIn, checkOut, user);

        Map<String, Integer> products = new HashMap<>();
        products.put("coke", 2);
        products.put("peanuts", 2);
        products.put("water", 2);
        products.put("chocolate", 2);

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(501.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(products);
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(420.0);
        bill.setCreationDate();
        bill.setBooking(booking);
        bill.setStatus("waiting");

        List<Bill> bills = new ArrayList<>();
        bills.add(bill);

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(billRepository.findByBooking(booking)).thenReturn(bills);

        List<Bill> expectedBill = billService.findByBooking(booking.getId());

        verify(bookingRepository, times(1)).findById(booking.getId());
        verify(billRepository, times(1)).findByBooking(booking);
        Assertions.assertThat(expectedBill).isNotNull();
    }
}
package com.senior.challenge.user.service;

import com.senior.challenge.user.dto.BookingDTO;
import com.senior.challenge.user.dto.ProductsDTO;
import com.senior.challenge.user.entity.Bill;
import com.senior.challenge.user.entity.Booking;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.enums.BookingStatus;
import com.senior.challenge.user.repository.BookingRepository;
import com.senior.challenge.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.*;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingServiceTest.class})
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private BillService billService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    private void setUp() {
        bookingService = new BookingService(bookingRepository, userService, billService);
    }

    @Test
    void saveBookingSuccessTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBegin(begin.getTime());
        bookingDTO.setEnd(end.getTime());
        bookingDTO.setGarage(true);
        bookingDTO.setUserId(user.getId());

        when(userService.findById(user.getId())).thenReturn(user);

        Booking booking = bookingService.save(bookingDTO);

        verify(bookingRepository, times(1)).save(booking);
        Assertions.assertThat(booking).isNotNull();
        Assertions.assertThat(booking.getUser().getDocument()).isEqualTo(user.getDocument());
    }

    @Test
    void bookingFoundWhenSavingTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBegin(begin.getTime());
        bookingDTO.setEnd(end.getTime());
        bookingDTO.setGarage(true);
        bookingDTO.setUserId(user.getId());

        Booking booking = Booking.create(bookingDTO);
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(bookingDTO.getBegin());
        booking.setEnd(bookingDTO.getEnd());
        booking.setGarage(bookingDTO.getGarage());
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(userService.findById(user.getId())).thenReturn(user);
        when(bookingRepository.findRegisteredBooking(user, begin.getTime(), end.getTime())).thenReturn(bookings);

        ResponseStatusException exception = Assert.assertThrows(ResponseStatusException.class, () -> {
            bookingService.save(bookingDTO);
        });

        verify(bookingRepository, times(0)).save(any());
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void findAllBookingsTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(begin.getTime());
        booking.setEnd(end.getTime());
        booking.setGarage(Boolean.TRUE);
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findAll()).thenReturn(bookings);
        List<Booking> expectedBookings = bookingService.findAll();

        verify(bookingRepository, times(1)).findAll();
        Assertions.assertThat(expectedBookings).isNotEmpty();
        Assertions.assertThat(expectedBookings.get(0).getId()).isEqualTo(booking.getId());
    }

    @Test
    void findBookingByIdTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(begin.getTime());
        booking.setEnd(end.getTime());
        booking.setGarage(Boolean.TRUE);
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        Booking expectedBooking = bookingService.findById(booking.getId());

        verify(bookingRepository, times(1)).findById(booking.getId());
        Assertions.assertThat(expectedBooking.getId()).isEqualTo(booking.getId());
    }

    @Test
    void updateBookingTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBegin(begin.getTime());
        bookingDTO.setEnd(end.getTime());
        bookingDTO.setGarage(true);
        bookingDTO.setUserId(user.getId());

        Booking booking = Booking.create(bookingDTO);
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(bookingDTO.getBegin());
        booking.setEnd(bookingDTO.getEnd());
        booking.setGarage(bookingDTO.getGarage());
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        Booking expectedBooking = bookingService.update(bookingDTO, booking.getId());

        verify(bookingRepository, times(1)).findById(booking.getId());
        Assertions.assertThat(expectedBooking.getUpdatedAt()).isNotNull();
    }

    @Test
    void checkOut() {

        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(begin.getTime());
        booking.setEnd(end.getTime());
        booking.setGarage(true);
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.getProducts().put("coke", 2);
        productsDTO.getProducts().put("peanuts", 2);
        productsDTO.getProducts().put("chocolate", 2);
        productsDTO.getProducts().put("water", 2);

        Bill bill = new Bill();
        bill.setId(UUID.randomUUID());
        bill.setTotal(351.0);
        bill.setProductsPrice(46.0);
        bill.setProducts(productsDTO.getProducts());
        bill.setGaragePrice(35.0);
        bill.setDailiesPrice(270.0);
        bill.setCreationDate();
        bill.setBooking(booking);
        bill.setStatus("waiting");

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(billService.calculateBillAndSave(booking, productsDTO.getProducts())).thenReturn(bill);

        Bill expectedBill = bookingService.checkOut(productsDTO, booking.getId());

        verify(bookingRepository, times(1)).save(booking);
        Assertions.assertThat(bill.getBooking().getId()).isEqualTo(booking.getId());
    }

    @Test
    void checkInBookingTest() {

        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        var begin = Calendar.getInstance();
        var end = Calendar.getInstance();

        begin.set(2021, 5, 18, 12, 00);
        end.set(2021, 5, 10, 16, 30);

        Booking booking = new Booking();
        booking.setId(UUID.randomUUID());
        booking.setUser(user);
        booking.setBegin(begin.getTime());
        booking.setEnd(end.getTime());
        booking.setGarage(true);
        booking.setStatus(BookingStatus.NEW.getStatus());
        booking.setCreationDate();

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        Booking expectedBooking = bookingService.checkIn(booking.getId());

        verify(bookingRepository, times(1)).save(booking);
        Assertions.assertThat(expectedBooking.getCheckIn()).isNotNull();
    }
}
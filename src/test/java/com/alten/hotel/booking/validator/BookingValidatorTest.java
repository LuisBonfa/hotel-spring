package com.alten.hotel.booking.validator;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.common.exception.exceptions.ValidationException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class BookingValidatorTest {

    @InjectMocks
    private BookingValidator bookingValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateBeginBeforeEndDateTest() {
        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBegin(LocalDate.of(2022, 9, 19));
        bookingDto.setEnd((LocalDate.of(2022, 9, 18)));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }

    @Test
    public void validateBeginEqualToCurrentDateTest() {
        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBegin(LocalDate.now());
        bookingDto.setEnd(LocalDate.now().plusDays(1));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }

    @Test
    public void validateBeginBeforeCurrentDateTest() {
        BookingDTO bookingDto = new BookingDTO();
        bookingDto.setBegin(LocalDate.of(2021, 9, 19));
        bookingDto.setEnd((LocalDate.of(2021, 9, 20)));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }

    @Test
    public void validateBookingPeriodTest() {
        BookingDTO bookingDto = new BookingDTO();
        LocalDate currentDate = LocalDate.now();
        bookingDto.setBegin(currentDate.plusDays(1));
        bookingDto.setEnd(currentDate.plusDays(4));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }

    @Test
    public void validateAdvanceBookingTest() {
        BookingDTO bookingDto = new BookingDTO();
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(30);
        bookingDto.setBegin(futureDate);
        bookingDto.setEnd(futureDate.plusDays(2));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }

    @Test
    public void validateAdvanceBookingSecondTest() {
        BookingDTO bookingDto = new BookingDTO();
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(32);
        bookingDto.setBegin(futureDate);
        bookingDto.setEnd(futureDate.plusDays(2));
        Assertions.assertThrows(ValidationException.class, () -> {
            bookingValidator.validate(bookingDto);
        });
    }
}
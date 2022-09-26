package com.alten.hotel.booking.validator;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.common.exception.exceptions.ValidationException;
import com.alten.hotel.common.validator.Validator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Validator used to verify the dates in a {@link BookingDTO}
 *
 * @author luis.bonfa
 */
@Component
public class BookingValidator implements Validator<BookingDTO> {

    @Override
    public void validate(BookingDTO data) {
        validateBeginBeforeEndDate(data);
        validateBeginAfterCurrentDate(data);
        validateBookingPeriod(data);
        validateAdvanceBooking(data);
    }

    /**
     * Validates if the beginning of the booking informed is
     * higher than the ending of booking.
     *
     * @param data {@link BookingDTO}
     */
    private void validateBeginBeforeEndDate(BookingDTO data) {
        if (data.getEnd().isBefore(data.getBegin())) {
            throw new ValidationException("End date must be after begin date!");
        }
    }

    /**
     * Validates if the beginning of the booking informed is
     * higher than the current date.
     *
     * @param data {@link BookingDTO}
     */
    private void validateBeginAfterCurrentDate(BookingDTO data) {
        LocalDate currentDate = LocalDate.now();
        if (data.getBegin().isBefore(currentDate) || data.getBegin().isEqual(currentDate)) {
            throw new ValidationException("Begin must be after current date!");
        }
    }

    /**
     * Validates if the period in days between the
     * beginning and ending of booking is equal or higher than 3 days.
     *
     * @param data {@link BookingDTO}
     */
    private void validateBookingPeriod(BookingDTO data) {
        long test = ChronoUnit.DAYS.between(data.getBegin(), data.getEnd());
        if (test >= 3) {
            throw new ValidationException("Booking Period Wrong");
        }
    }

    /**
     * Validates if the beginning of the booking informed is
     * at most 29 days in the future.
     *
     * @param data {@link BookingDTO}
     */
    private void validateAdvanceBooking(BookingDTO data) {
        LocalDate currentDate = LocalDate.now();
        long test = ChronoUnit.DAYS.between(currentDate, data.getBegin());
        if (test >= 30) {
            throw new ValidationException("Booking Period too advanced");
        }
    }
}

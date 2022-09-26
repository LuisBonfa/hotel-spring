package com.alten.hotel.booking.mapper;

import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Mapper used to map an {@link BookingEntity}
 * to a {@link BookingRecord}.
 *
 * @author luis.bonfa
 */
@Component
public class BookingEntityToRecordMapper implements Mapper<BookingEntity, BookingRecord> {

    @Override
    public BookingRecord mapNonNull(BookingEntity bookingEntity) {
        return BookingRecord.builder()
                .bookingId(bookingEntity.getId())
                .userId(bookingEntity.getUser().getId())
                .bookingCode(bookingEntity.getBookingCode())
                .begin(LocalDate.ofInstant(bookingEntity.getBegin(), ZoneId.of("UTC")))
                .end((LocalDate.ofInstant(bookingEntity.getEnd(), ZoneId.of("UTC"))))
                .status(bookingEntity.getStatus())
                .build();
    }
}

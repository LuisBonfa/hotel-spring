package com.alten.hotel.bookinghistory.mapper;

import com.alten.hotel.bookinghistory.dto.BookingHistoryRecord;
import com.alten.hotel.bookinghistory.entity.BookingHistoryEntity;
import com.alten.hotel.common.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class BookingHistoryEntityToRecordMapper implements Mapper<BookingHistoryEntity, BookingHistoryRecord> {

    @Override
    public BookingHistoryRecord mapNonNull(BookingHistoryEntity bookingHistory) {
        return BookingHistoryRecord.builder()
                .bookingId(bookingHistory.getBooking().getId())
                .begin(LocalDate.ofInstant(bookingHistory.getBegin(), ZoneId.of("UTC")))
                .end(LocalDate.ofInstant(bookingHistory.getEnd(), ZoneId.of("UTC")))
                .build();
    }
}

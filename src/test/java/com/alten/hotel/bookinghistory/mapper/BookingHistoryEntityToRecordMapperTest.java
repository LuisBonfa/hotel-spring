package com.alten.hotel.bookinghistory.mapper;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.bookinghistory.dto.BookingHistoryRecord;
import com.alten.hotel.bookinghistory.entity.BookingHistoryEntity;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingHistoryEntityToRecordMapperTest {

    @Spy
    BookingHistoryEntityToRecordMapper bookingHistoryRecordMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        Instant begin = LocalDate.of(2022, 9, 9).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = LocalDate.of(2022, 9, 12).atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        BookingEntity booking = new BookingEntity();
        booking.setId(UUID.randomUUID());
        booking.setBegin(begin);
        booking.setEnd(end);
        booking.setUser(userEntity);
        booking.setBookingCode(String.valueOf(booking.hashCode()));
        booking.setStatus(CommonStatus.ACTIVE);

        BookingHistoryEntity bookingHistory = new BookingHistoryEntity();
        bookingHistory.setBooking(booking);
        bookingHistory.setBegin(booking.getBegin());
        bookingHistory.setEnd(booking.getEnd());

        BookingHistoryRecord bookingRecord = bookingHistoryRecordMapper.mapNonNull(bookingHistory);

        Assertions.assertThat(bookingHistory.getBegin()).isEqualTo(bookingRecord.getBegin().atStartOfDay().atOffset(ZoneOffset.UTC).toInstant());
        Assertions.assertThat(bookingHistory.getEnd()).isEqualTo(bookingRecord.getEnd().atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant());
        Assertions.assertThat(bookingHistory.getBooking().getId()).isEqualTo(bookingRecord.getBookingId());
    }
}
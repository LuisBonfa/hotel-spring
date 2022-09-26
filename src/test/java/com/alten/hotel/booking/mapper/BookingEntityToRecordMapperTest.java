package com.alten.hotel.booking.mapper;

import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.user.entity.UserEntity;
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
public class BookingEntityToRecordMapperTest {

    @Spy
    private BookingEntityToRecordMapper bookingDtoMapper;

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

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(begin);
        bookingEntity.setEnd(end);
        bookingEntity.setUser(userEntity);
        bookingEntity.setBookingCode(String.valueOf(bookingEntity.hashCode()));
        bookingEntity.setStatus(CommonStatus.ACTIVE);

        BookingRecord bookingRecord = bookingDtoMapper.mapNonNull(bookingEntity);

        Assertions.assertThat(bookingEntity.getBegin()).isEqualTo(bookingRecord.getBegin().atStartOfDay().atOffset(ZoneOffset.UTC).toInstant());
        Assertions.assertThat(bookingEntity.getEnd()).isEqualTo(bookingRecord.getEnd().atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant());
        Assertions.assertThat(bookingEntity.getUser().getId()).isEqualTo(bookingRecord.getUserId());
        Assertions.assertThat(bookingEntity.getBookingCode()).isEqualTo(bookingRecord.getBookingCode());
        Assertions.assertThat(bookingEntity.getId()).isEqualTo(bookingRecord.getBookingId());
        Assertions.assertThat(bookingEntity.getStatus()).isEqualTo(bookingRecord.getStatus());
    }
}
package com.alten.hotel.booking.mapper;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.utils.DateControl;
import com.alten.hotel.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingDtoToEntityMapperTest {

    @Spy
    @InjectMocks
    BookingDtoToEntityMapper bookingEntityMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mapNonNullTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        BookingDTO dto = new BookingDTO();
        dto.setBegin(LocalDate.of(2022, 9, 20));
        dto.setEnd(LocalDate.of(2022, 9, 22));
        dto.setUserId(userEntity.getId());

        BookingEntity bookingEntity = bookingEntityMapper.mapNonNull(dto);

        Assertions.assertThat(DateControl.convertLocalDateToInstantBegin(dto.getBegin())).isEqualTo(bookingEntity.getBegin());
        Assertions.assertThat(DateControl.convertLocalDateToInstantEnd(dto.getEnd())).isEqualTo(bookingEntity.getEnd());
        Assertions.assertThat(CommonStatus.ACTIVE).isEqualTo(bookingEntity.getStatus());
        Assertions.assertThat(bookingEntity.getBookingCode()).isNotNull();
        Assertions.assertThat(userEntity.getId()).isEqualTo(bookingEntity.getUser().getId());
    }
}
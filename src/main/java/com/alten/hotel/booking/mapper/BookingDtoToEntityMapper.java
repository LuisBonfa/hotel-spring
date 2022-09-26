package com.alten.hotel.booking.mapper;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.utils.DateControl;
import com.alten.hotel.user.entity.UserEntity;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Mapper used to map an {@link BookingDTO}
 * to a {@link BookingEntity}.
 *
 * @author luis.bonfa
 */
@Component
public class BookingDtoToEntityMapper implements Mapper<BookingDTO, BookingEntity> {

    @Override
    public BookingEntity mapNonNull(@NonNull BookingDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(dto.getUserId());

        BookingEntity entity = new BookingEntity();
        entity.setBegin(DateControl.convertLocalDateToInstantBegin(dto.getBegin()));
        entity.setEnd(DateControl.convertLocalDateToInstantEnd(dto.getEnd()));
        entity.setBookingCode(String.valueOf(entity.hashCode()));
        entity.setUser(userEntity);
        entity.setStatus(CommonStatus.ACTIVE);
        return entity;
    }
}

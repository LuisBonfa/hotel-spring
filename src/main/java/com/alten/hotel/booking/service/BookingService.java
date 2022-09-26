package com.alten.hotel.booking.service;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.bookinghistory.service.BookingHistoryService;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.exception.exceptions.UnauthorizedException;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.utils.DateControl;
import com.alten.hotel.common.validator.Validator;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingService {

    BookingRepository bookingRepository;
    Mapper<BookingDTO, BookingEntity> bookingEntityMapper;
    Mapper<BookingEntity, BookingRecord> bookingRecordMapper;
    Validator<BookingDTO> bookingValidator;
    BookingHistoryService bookingHistoryService;

    /**
     * List all of bookings registered.
     *
     * @return {@link List<BookingRecord>}
     */
    public List<BookingRecord> findAll() {
        return bookingRepository.findByStatusIs(CommonStatus.ACTIVE).stream().map(bookingRecordMapper::mapNonNull).collect(Collectors.toList());
    }

    public BookingRecord save(BookingDTO dto) {
        bookingValidator.validate(dto);
        BookingEntity entity = bookingEntityMapper.mapNonNull(dto);
        if (!verifyBookingExistence(entity.getBegin(), entity.getEnd())) {
            return bookingRecordMapper.mapNonNull(bookingRepository.save(entity));
        }
        throw new UnauthorizedException("This date is not available for booking!");
    }

    public boolean verifyBookingExistence(Instant beginDate, Instant endDate) {
        return bookingRepository.existsBetweenDates(beginDate, endDate);
    }

    public BookingRecord delete(String bookingId) {
        Optional<BookingEntity> booking = bookingRepository.findById(UUID.fromString(bookingId));
        if (booking.isPresent()) {
            BookingEntity bookingEntity = booking.get();
            bookingEntity.setStatus(CommonStatus.UNACTIVE);
            bookingEntity.setUpdatedAt(Instant.now());
            return bookingRecordMapper.mapNonNull(bookingRepository.save(bookingEntity));
        }
        throw new NotFoundException("Couldn't find booking with this id");
    }

    public BookingRecord update(String bookingId, BookingDTO dto) {
        bookingValidator.validate(dto);
        Instant instantBegin = DateControl.convertLocalDateToInstantBegin(dto.getBegin());
        Instant instantEnd = DateControl.convertLocalDateToInstantEnd(dto.getEnd());
        if (!verifyBookingExistence(instantBegin, instantEnd)) {
            Optional<BookingEntity> booking = bookingRepository.findById(UUID.fromString(bookingId));
            if (booking.isPresent()) {
                BookingEntity bookingEntity = booking.get();
                bookingHistoryService.save(bookingEntity);

                bookingEntity.setBegin(instantBegin);
                bookingEntity.setEnd(instantEnd);
                bookingEntity.setUpdatedAt(Instant.now());
                return bookingRecordMapper.mapNonNull(bookingRepository.save(bookingEntity));
            }
            throw new NotFoundException("Couldn't find booking with this id");
        }
        throw new UnauthorizedException("This date is not available for booking!");
    }
}
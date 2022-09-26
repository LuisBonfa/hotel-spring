package com.alten.hotel.bookinghistory.service;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.bookinghistory.dto.BookingHistoryRecord;
import com.alten.hotel.bookinghistory.entity.BookingHistoryEntity;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.mapper.Mapper;
import com.alten.hotel.common.service.FindAll;
import com.alten.hotel.common.service.Save;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingHistoryService implements FindAll<BookingHistoryRecord>, Save<BookingEntity, BookingHistoryEntity> {

    BookingHistoryRepository repository;
    Mapper<BookingHistoryEntity, BookingHistoryRecord> bookingHistoryRecordMapper;
    
    @Override
    public List<BookingHistoryRecord> findAll() {
        return repository.findAll().stream().map(bookingHistoryRecordMapper::mapNonNull).collect(Collectors.toList());
    }

    @Override
    public BookingHistoryEntity save(BookingEntity bookingEntity) {
        BookingHistoryEntity bookingHistory = new BookingHistoryEntity();
        bookingHistory.setBegin(bookingEntity.getBegin());
        bookingHistory.setEnd(bookingEntity.getEnd());
        bookingHistory.setBooking(bookingEntity);
        return repository.save(bookingHistory);
    }
}

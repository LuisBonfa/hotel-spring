package com.alten.hotel.bookinghistory.service;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.bookinghistory.dto.BookingHistoryRecord;
import com.alten.hotel.bookinghistory.entity.BookingHistoryEntity;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.mapper.Mapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingHistoryServiceTest {

    @Mock
    BookingHistoryRepository bookingHistoryRepository;

    @Mock
    Mapper<BookingHistoryEntity, BookingHistoryRecord> bookingHistoryRecordMapper;

    @Spy
    @InjectMocks
    BookingHistoryService bookingHistoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest() {
        Instant currentTime = Instant.now();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(currentTime);
        bookingEntity.setEnd(currentTime);

        BookingHistoryEntity bookingHistory = new BookingHistoryEntity();
        bookingHistory.setBooking(bookingEntity);
        bookingHistory.setBegin(currentTime);
        bookingEntity.setEnd(currentTime);

        when(bookingHistoryRepository.findAll()).thenReturn(List.of(bookingHistory));

        List<BookingHistoryRecord> bookings = bookingHistoryService.findAll();

        Assertions.assertThat(1).isEqualTo(bookings.size());

        verify(bookingHistoryRepository, times(1)).findAll();
        verify(bookingHistoryRecordMapper, times(1)).mapNonNull(bookingHistory);

        InOrder order = Mockito.inOrder(bookingHistoryRepository, bookingHistoryRecordMapper);
        order.verify(bookingHistoryRepository).findAll();
        order.verify(bookingHistoryRecordMapper).mapNonNull(bookingHistory);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void saveTest() {
        Instant currentTime = Instant.now();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(currentTime);
        bookingEntity.setEnd(currentTime);

        BookingHistoryEntity bookingHistory = new BookingHistoryEntity();
        bookingHistory.setBooking(bookingEntity);
        bookingHistory.setBegin(currentTime);
        bookingHistory.setEnd(currentTime);

        when(bookingHistoryRepository.save(any())).thenReturn(bookingHistory);

        BookingHistoryEntity result = bookingHistoryService.save(bookingEntity);

        Assertions.assertThat(bookingEntity).usingRecursiveComparison().isEqualTo(result.getBooking());

        verify(bookingHistoryRepository, times(1)).save(any());

        InOrder order = Mockito.inOrder(bookingHistoryRepository);
        order.verify(bookingHistoryRepository).save(any());
        order.verifyNoMoreInteractions();
    }
}
package com.alten.hotel.booking.service;

import com.alten.hotel.booking.dto.BookingDTO;
import com.alten.hotel.booking.dto.BookingRecord;
import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.booking.mapper.BookingDtoToEntityMapper;
import com.alten.hotel.booking.mapper.BookingEntityToRecordMapper;
import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.booking.validator.BookingValidator;
import com.alten.hotel.bookinghistory.service.BookingHistoryService;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.exception.exceptions.UnauthorizedException;
import com.alten.hotel.user.entity.UserEntity;
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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    BookingDtoToEntityMapper bookingEntityMapper;

    @Mock
    BookingEntityToRecordMapper bookingRecordMapper;

    @Mock
    BookingValidator bookingValidator;

    @Mock
    BookingHistoryService bookingHistoryService;

    @Spy
    @InjectMocks
    BookingService bookingService;

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

        when(bookingRepository.findByStatusIs(CommonStatus.ACTIVE)).thenReturn(List.of(bookingEntity));

        List<BookingRecord> bookings = bookingService.findAll();

        Assertions.assertThat(1).isEqualTo(bookings.size());

        verify(bookingRepository, times(1)).findByStatusIs(CommonStatus.ACTIVE);

        InOrder order = Mockito.inOrder(bookingRepository);
        order.verify(bookingRepository).findByStatusIs(CommonStatus.ACTIVE);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void foundByIdTest() {
        Instant currentTime = Instant.now();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(currentTime);
        bookingEntity.setEnd(currentTime);

        BookingRecord bookingRecord = BookingRecord.builder().bookingId(bookingEntity.getId()).build();

        when(bookingRepository.findById(bookingEntity.getId())).thenReturn(Optional.of(bookingEntity));
        when(bookingRecordMapper.mapNonNull(bookingEntity)).thenReturn(bookingRecord);

        BookingRecord booking = bookingService.findById(bookingEntity.getId());

        Assertions.assertThat(bookingEntity.getId()).isEqualTo(booking.getBookingId());

        verify(bookingRepository, times(1)).findById(bookingEntity.getId());
        verify(bookingRecordMapper, times(1)).mapNonNull(bookingEntity);

        InOrder order = Mockito.inOrder(bookingRepository, bookingRecordMapper);
        order.verify(bookingRepository).findById(bookingEntity.getId());
        order.verify(bookingRecordMapper).mapNonNull(bookingEntity);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void foundNoneByIdTest() {
        UUID bookingId = UUID.randomUUID();

        assertThrows(NotFoundException.class, () -> {
            bookingService.findById(bookingId);
        });

        verify(bookingRepository, times(1)).findById(bookingId);

        InOrder order = Mockito.inOrder(bookingRepository);
        order.verify(bookingRepository).findById(bookingId);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void cancelWithBookingFoundTest() {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());

        BookingRecord bookingRecord = BookingRecord.builder().bookingId(bookingEntity.getId()).status(CommonStatus.UNACTIVE).build();

        when(bookingRepository.findById(bookingEntity.getId())).thenReturn(Optional.of(bookingEntity));
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingRecordMapper.mapNonNull(bookingEntity)).thenReturn(bookingRecord);

        BookingRecord bookingResult = bookingService.delete(bookingEntity.getId());

        Assertions.assertThat(CommonStatus.UNACTIVE).isEqualTo(bookingResult.getStatus());

        verify(bookingRepository, times(1)).findById(bookingEntity.getId());
        verify(bookingRepository, times(1)).save(bookingEntity);
        verify(bookingRecordMapper, times(1)).mapNonNull(bookingEntity);

        InOrder order = Mockito.inOrder(bookingRepository, bookingRecordMapper);
        order.verify(bookingRepository).findById(bookingEntity.getId());
        order.verify(bookingRepository).save(bookingEntity);
        order.verify(bookingRecordMapper).mapNonNull(bookingEntity);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void cancelWithBookingNotFoundTest() {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            bookingService.delete(bookingEntity.getId());
        });

        Assertions.assertThat(exception.getMessage().contains("Couldn't find booking with this id")).isTrue();

        verify(bookingRepository, times(1)).findById(bookingEntity.getId());

        InOrder order = Mockito.inOrder(bookingRepository);
        order.verify(bookingRepository).findById(bookingEntity.getId());
        order.verifyNoMoreInteractions();

    }

    @Test
    public void verifyBookingAvailabilityTrueTest() {
        Instant begin = LocalDate.of(2022, 9, 21).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 22).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();
        when(bookingRepository.existsBetweenDates(begin, end)).thenReturn(true);
        boolean exists = bookingService.verifyBookingExistence(begin, end);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void verifyBookingAvailabilityFalseTest() {
        Instant begin = LocalDate.of(2022, 9, 21).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 22).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();
        when(bookingRepository.existsBetweenDates(begin, end)).thenReturn(false);
        boolean exists = bookingService.verifyBookingExistence(begin, end);

        Assertions.assertThat(exists).isFalse();
    }

    @Test
    public void saveWorkingTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        Instant begin = LocalDate.of(2022, 9, 20).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 22).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(begin);
        bookingEntity.setEnd(end);
        bookingEntity.setUser(userEntity);

        LocalDate dtoBegin = (LocalDate.of(2022, 9, 20));
        LocalDate dtoEnd = (LocalDate.of(2022, 9, 22));

        BookingDTO dto = new BookingDTO();
        dto.setBegin(dtoBegin);
        dto.setEnd(dtoEnd);
        dto.setUserId(userEntity.getId());

        BookingRecord bookingRecord = BookingRecord.builder().bookingId(bookingEntity.getId())
                .userId(userEntity.getId())
                .begin(dtoBegin)
                .end(dtoEnd)
                .bookingCode(String.valueOf(bookingEntity.hashCode())).build();

        doNothing().when(bookingValidator).validate(dto);
        when(bookingEntityMapper.mapNonNull(dto)).thenReturn(bookingEntity);
        doReturn(false).when(bookingService).verifyBookingExistence(begin, end);
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingRecordMapper.mapNonNull(bookingEntity)).thenReturn(bookingRecord);

        BookingRecord bookingResult = bookingService.save(dto);

        Assertions.assertThat(bookingEntity.getId()).isEqualTo(bookingResult.getBookingId());
        Assertions.assertThat(userEntity.getId()).isEqualTo(bookingResult.getUserId());
        Assertions.assertThat(dtoBegin).isEqualTo(bookingResult.getBegin());
        Assertions.assertThat(dtoEnd).isEqualTo(bookingResult.getEnd());

        verify(bookingValidator, times(1)).validate(dto);
        verify(bookingEntityMapper, times(1)).mapNonNull(dto);
        verify(bookingService, times(1)).verifyBookingExistence(begin, end);
        verify(bookingRepository, times(1)).save(bookingEntity);
        verify(bookingRecordMapper, times(1)).mapNonNull(bookingEntity);

        InOrder order = Mockito.inOrder(bookingValidator, bookingEntityMapper, bookingService, bookingRepository, bookingRecordMapper);
        order.verify(bookingValidator).validate(dto);
        order.verify(bookingEntityMapper).mapNonNull(dto);
        order.verify(bookingService).verifyBookingExistence(begin, end);
        order.verify(bookingRepository).save(bookingEntity);
        order.verify(bookingRecordMapper).mapNonNull(bookingEntity);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void saveWithoutValidBookingDateTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        Instant begin = LocalDate.of(2022, 9, 20).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 22).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(begin);
        bookingEntity.setEnd(end);
        bookingEntity.setUser(userEntity);

        BookingDTO dto = new BookingDTO();
        dto.setBegin(LocalDate.of(2022, 9, 20));
        dto.setEnd(LocalDate.of(2022, 9, 22));
        dto.setUserId(userEntity.getId());

        doNothing().when(bookingValidator).validate(dto);
        when(bookingEntityMapper.mapNonNull(dto)).thenReturn(bookingEntity);
        doReturn(true).when(bookingService).verifyBookingExistence(begin, end);

        assertThrows(UnauthorizedException.class, () -> {
            bookingService.save(dto);
        });

        verify(bookingValidator, times(1)).validate(dto);
        verify(bookingEntityMapper, times(1)).mapNonNull(dto);
        verify(bookingService, times(1)).verifyBookingExistence(begin, end);

        InOrder order = Mockito.inOrder(bookingValidator, bookingEntityMapper, bookingService);
        order.verify(bookingValidator).validate(dto);
        order.verify(bookingEntityMapper).mapNonNull(dto);
        order.verify(bookingService).verifyBookingExistence(begin, end);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void updateWorkingTest() {
        LocalDate oldBegin = LocalDate.of(2022, 9, 20);
        LocalDate oldEnd = LocalDate.of(2022, 9, 22);
        LocalDate begin = LocalDate.of(2022, 9, 25);
        LocalDate end = LocalDate.of(2022, 9, 26);

        Instant instantBegin = begin.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant instantEnd = end.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(UUID.randomUUID());
        bookingEntity.setBegin(oldBegin.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant());
        bookingEntity.setEnd(oldEnd.atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant());
        bookingEntity.setUser(userEntity);

        BookingDTO dto = new BookingDTO();
        dto.setBegin(LocalDate.of(2022, 9, 25));
        dto.setEnd(LocalDate.of(2022, 9, 26));
        dto.setUserId(userEntity.getId());

        BookingRecord bookingRecord = BookingRecord.builder().bookingId(bookingEntity.getId()).begin(begin).end(end).userId(userEntity.getId()).build();

        doNothing().when(bookingValidator).validate(dto);
        doReturn(false).when(bookingService).verifyBookingExistence(instantBegin, instantEnd);
        when(bookingRepository.findById(bookingEntity.getId())).thenReturn(Optional.of(bookingEntity));
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingRecordMapper.mapNonNull(bookingEntity)).thenReturn(bookingRecord);

        BookingRecord bookingUpdated = bookingService.update(bookingEntity.getId(), dto);

        Assertions.assertThat(begin).isEqualTo(bookingUpdated.getBegin());
        Assertions.assertThat(end).isEqualTo(bookingUpdated.getEnd());

        verify(bookingValidator, times(1)).validate(dto);
        verify(bookingService, times(1)).verifyBookingExistence(instantBegin, instantEnd);
        verify(bookingRepository, times(1)).findById(bookingEntity.getId());
        verify(bookingRepository, times(1)).save(bookingEntity);
        verify(bookingRecordMapper, times(1)).mapNonNull(bookingEntity);
        verify(bookingHistoryService, times(1)).save(bookingEntity);

        InOrder order = Mockito.inOrder(bookingValidator, bookingService, bookingRepository, bookingHistoryService, bookingRecordMapper);
        order.verify(bookingValidator).validate(dto);
        order.verify(bookingService).verifyBookingExistence(instantBegin, instantEnd);
        order.verify(bookingRepository).findById(bookingEntity.getId());
        order.verify(bookingHistoryService).save(bookingEntity);
        order.verify(bookingRepository).save(bookingEntity);
        order.verify(bookingRecordMapper).mapNonNull(bookingEntity);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void updateWithBookingNotPresentTest() {
        Instant begin = LocalDate.of(2022, 9, 25).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 26).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        UUID bookingId = UUID.randomUUID();

        BookingDTO dto = new BookingDTO();
        dto.setBegin(LocalDate.of(2022, 9, 25));
        dto.setEnd(LocalDate.of(2022, 9, 26));

        doNothing().when(bookingValidator).validate(dto);
        doReturn(false).when(bookingService).verifyBookingExistence(begin, end);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            bookingService.update(bookingId, dto);
        });

        verify(bookingValidator, times(1)).validate(dto);
        verify(bookingService, times(1)).verifyBookingExistence(begin, end);
        verify(bookingRepository, times(1)).findById(bookingId);

        InOrder order = Mockito.inOrder(bookingValidator, bookingService, bookingRepository);
        order.verify(bookingValidator).validate(dto);
        order.verify(bookingService).verifyBookingExistence(begin, end);
        order.verify(bookingRepository).findById(bookingId);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void updateWithNewBookingDateAlreadyFilledTest() {
        Instant begin = LocalDate.of(2022, 9, 25).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 26).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();
        UUID bookingId = UUID.randomUUID();

        BookingDTO dto = new BookingDTO();
        dto.setBegin(LocalDate.of(2022, 9, 25));
        dto.setEnd(LocalDate.of(2022, 9, 26));

        doNothing().when(bookingValidator).validate(dto);
        doReturn(true).when(bookingService).verifyBookingExistence(begin, end);

        assertThrows(UnauthorizedException.class, () -> {
            bookingService.update(bookingId, dto);
        });

        verify(bookingValidator, times(1)).validate(dto);
        verify(bookingService, times(1)).verifyBookingExistence(begin, end);

        InOrder order = Mockito.inOrder(bookingValidator, bookingService);
        order.verify(bookingValidator).validate(dto);
        order.verify(bookingService).verifyBookingExistence(begin, end);
        order.verifyNoMoreInteractions();
    }
}
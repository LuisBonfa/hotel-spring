package com.alten.hotel.booking.repository;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        bookingHistoryRepository.deleteAll();
        bookingRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void existsBetweenDatesFalseTest() {
        insertUserAndBooking();

        Instant begin = LocalDate.of(2022, 9, 21).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 22).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        boolean isAvailable = bookingRepository.existsBetweenDates(begin, end);

        Assertions.assertThat(isAvailable).isFalse();
    }

    @Test
    public void existsBetweenDatesTrueTest() {
        insertUserAndBooking();

        Instant begin = LocalDate.of(2022, 9, 19).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 20).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        boolean isAvailable = bookingRepository.existsBetweenDates(begin, end);

        Assertions.assertThat(isAvailable).isTrue();
    }

    private void insertUserAndBooking() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setAlias("alias");
        userEntity.setTries(0);
        userEntity.setEmail("email");
        userEntity.setPhone("phone");
        userEntity.setDocument("document");
        userEntity.setStatus(CommonStatus.ACTIVE);
        userRepository.save(userEntity);

        Instant begin = LocalDate.of(2022, 9, 19).atStartOfDay().atOffset(ZoneOffset.UTC).toInstant();
        Instant end = LocalDate.of(2022, 9, 20).atTime(23, 59, 59).atOffset(ZoneOffset.UTC).toInstant();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBegin(begin);
        bookingEntity.setEnd(end);
        bookingEntity.setUser(userEntity);
        bookingEntity.setStatus(CommonStatus.ACTIVE);
        bookingEntity.setBookingCode(String.valueOf(bookingEntity.hashCode()));
        bookingRepository.save(bookingEntity);
    }
}
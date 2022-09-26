package com.alten.hotel.bookinghistory.repository;

import com.alten.hotel.bookinghistory.entity.BookingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistoryEntity, UUID> {
}

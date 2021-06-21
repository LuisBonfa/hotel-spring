package com.senior.challenge.user.repository;

import com.senior.challenge.user.entity.Booking;
import com.senior.challenge.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Modifying
    @Query("select b from Booking b where b.user = :user and b.status != 'canceled' and b.status != 'ended' and (b.begin = :begin or b.end = :end) ")
    List<Booking> findRegisteredBooking(@Param("user") User user, @Param("begin") Date begin, @Param("end") Date end);
}

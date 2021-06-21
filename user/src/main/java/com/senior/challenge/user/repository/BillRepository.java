package com.senior.challenge.user.repository;

import com.senior.challenge.user.entity.Bill;
import com.senior.challenge.user.entity.Booking;
import com.senior.challenge.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByBooking(@Param("booking") Booking booking);

}

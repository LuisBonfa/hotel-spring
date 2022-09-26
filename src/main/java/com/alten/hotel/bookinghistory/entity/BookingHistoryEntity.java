package com.alten.hotel.bookinghistory.entity;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.bookinghistory.repository.BookingHistoryRepository;
import com.alten.hotel.common.persistence.Creatable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Entity used to store information
 * about histories of bookings.
 * <p>
 * Repository: {@link BookingHistoryRepository}
 *
 * @author luis.bonfa
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking_history")
public class BookingHistoryEntity extends Creatable {

    @ManyToOne
    @NotNull(message = "booking is required")
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "booking_booking_history_fk"))
    private BookingEntity booking;

    @NotNull(message = "Begin date is required")
    @Column(name = "booking_begin")
    private Instant begin;

    @NotNull(message = "End date is required")
    @Column(name = "booking_end")
    private Instant end;
}

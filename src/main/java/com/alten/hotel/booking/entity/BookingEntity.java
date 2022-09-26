package com.alten.hotel.booking.entity;

import com.alten.hotel.booking.repository.BookingRepository;
import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.persistence.Updatable;
import com.alten.hotel.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Entity used to store information
 * about bookings.
 * <p>
 * Repository: {@link BookingRepository}
 *
 * @author luis.bonfa
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "booking")
public class BookingEntity extends Updatable {

    @ManyToOne
    @NotNull(message = "User is required")
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_booking_fk"))
    private UserEntity user;

    @NotNull(message = "Begin date is required")
    @Column(name = "booking_begin")
    private Instant begin;

    @NotNull(message = "End date is required")
    @Column(name = "booking_end")
    private Instant end;

    @NotNull(message = "Booking code is required")
    @Column(name = "booking_code")
    private String bookingCode;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private CommonStatus status;
}

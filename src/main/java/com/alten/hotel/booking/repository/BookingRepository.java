package com.alten.hotel.booking.repository;

import com.alten.hotel.booking.entity.BookingEntity;
import com.alten.hotel.common.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Repository of the entity {@link BookingEntity}
 *
 * @author luis.bonfa
 */
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findByStatusIs(CommonStatus status);

    /**
     * Query used to search if a booking exists between dates. This
     * query evaluates if the start date informed is between any already registered booking
     * and does the same to the end date informed, if the query finds anything, it verifies
     * if the register found is currently active.
     *
     * @param beginDate beginning of booking {@link Instant}
     * @param endDate   ending of booking {@link Instant}
     * @return a boolean if exists or not
     */
    @Query("SELECT count(b)>0 FROM BookingEntity b where ((:beginDate between b.begin and b.end) or (:endDate between b.begin and b.end)) and status = 'ACTIVE'")
    boolean existsBetweenDates(@Param("beginDate") Instant beginDate, @Param("endDate") Instant endDate);

}

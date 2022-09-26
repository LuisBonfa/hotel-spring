package com.alten.hotel.common.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created to handle all date conversions that are going
 * to be needed;
 *
 * @author luis.bonfa
 */
@UtilityClass
public class DateControl {

    /**
     * Converts a {@link LocalDateTime} to {@link Instant}
     *
     * @param localDateTime date to be converted {@link LocalDateTime}
     * @return {@link Instant}
     */
    private static Instant convertLocalDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    /**
     * Converts a {@link LocalDate} to {@link Instant}
     * at the beginning of the day. Example: 2022-09-20 00:00:00
     *
     * @param localDate date to be converted {@link LocalDate}
     * @return {@link Instant}
     */
    public static Instant convertLocalDateToInstantBegin(LocalDate localDate) {
        return convertLocalDateTimeToInstant(localDate.atStartOfDay());
    }

    /**
     * Converts a {@link LocalDate} to {@link Instant}
     * at the end of the day. Example: 2022-09-20 23:59:59
     *
     * @param localDate date to be converted {@link LocalDate}
     * @return {@link Instant}
     */
    public static Instant convertLocalDateToInstantEnd(LocalDate localDate) {
        return convertLocalDateTimeToInstant(localDate.atTime(23, 59, 59));
    }
}

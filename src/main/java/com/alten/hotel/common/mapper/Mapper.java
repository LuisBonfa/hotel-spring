package com.alten.hotel.common.mapper;

import org.springframework.lang.NonNull;

/**
 * Interface created to implement Mappers
 * and allow overrides if needed.
 *
 * @param <T> Initial object
 * @param <U> Object that it's going to be mapped to
 * @author luis.bonfa
 */
public interface Mapper<T, U> {

    /**
     * Maps the object to another object.
     *
     * @param element Initial object {@link T}
     * @return {@link U}
     */
    U mapNonNull(@NonNull T element);
}

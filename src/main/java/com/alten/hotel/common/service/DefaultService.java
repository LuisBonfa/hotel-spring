package com.alten.hotel.common.service;

import java.util.List;
import java.util.UUID;

/**
 * Used to optimize the creations of services
 *
 * @param <D> the DTO that it's going to be used
 * @param <R> the Resource that it's going to be used
 * @author luis.bonfa
 */
public interface DefaultService<D, R> {

    List<R> findAll();

    R findById(UUID id);

    R save(D data);

    R update(UUID id, D data);

    R delete(UUID id);
}

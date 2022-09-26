package com.alten.hotel.common.service;

/**
 * Used to optimize the creations of services
 *
 * @param <D> the DTO that it's going to be used
 * @param <R> the Resource that it's going to be used
 * @author luis.bonfa
 */
public interface DefaultService<D, R> extends Save<D, R>, Update<D, R>, FindAll<R>, FindById<R>, Delete<R> {
}

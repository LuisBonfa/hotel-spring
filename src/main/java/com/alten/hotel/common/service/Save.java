package com.alten.hotel.common.service;

public interface Save<D, R> {
    R save(D data);
}

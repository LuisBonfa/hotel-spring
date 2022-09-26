package com.alten.hotel.common.service;

import java.util.UUID;

public interface FindById<R> {
    R findById(UUID id);
}

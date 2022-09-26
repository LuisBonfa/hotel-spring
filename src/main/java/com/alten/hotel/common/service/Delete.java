package com.alten.hotel.common.service;

import java.util.UUID;

public interface Delete<R> {
    R delete(UUID id);
}

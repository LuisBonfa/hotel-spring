package com.alten.hotel.common.service;

import java.util.UUID;

public interface Update<D, R> {

    R update(UUID id, D data);
}

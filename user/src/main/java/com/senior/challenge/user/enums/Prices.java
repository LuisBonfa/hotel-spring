package com.senior.challenge.user.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Prices {
    ROOM_WEEKDAY(1,120),
    ROOM_WEEKEND(2,150),
    GARAGE_WEEKDAY(3,15),
    GARAGE_WEEKEND(4,20),
    COKE(5, 6),
    CHOCOLATE(6, 8),
    WATER(7, 4),
    PEANUTS(8, 5);

    private int id;
    private int price;

    Prices(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }
}

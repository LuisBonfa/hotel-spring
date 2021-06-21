package com.senior.challenge.user.enums;

public enum BookingStatus {
    NEW("new"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    CANCELED("canceled"),
    ENDED("ended");

    private String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

package com.senior.challenge.user.utils;

import java.util.Calendar;

public class Validator {

    //Verify if the date is a weekday ou weekend
    public static boolean verifyWeekend(Calendar begin) {
        if (begin.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        return false;
    }
}

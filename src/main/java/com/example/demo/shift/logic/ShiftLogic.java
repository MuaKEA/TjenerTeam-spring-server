package com.example.demo.shift.logic;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ShiftLogic {
    public static boolean isActiveShift(String eventDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(eventDate)) > 0;
    }
}

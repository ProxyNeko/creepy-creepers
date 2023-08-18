package dev.nertzhul.creepycreepers.util;

import java.time.LocalDate;
import java.time.Month;

public class CalendarUtil {
    public static boolean isChristmas() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() == 25;
    }
    
    public static boolean isHalloween() {
        LocalDate date = LocalDate.now();
        return date.getMonth() == Month.OCTOBER && date.getDayOfMonth() == 31;
    }
}

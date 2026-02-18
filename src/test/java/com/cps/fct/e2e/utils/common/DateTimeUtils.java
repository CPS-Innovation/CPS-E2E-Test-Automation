package com.cps.fct.e2e.utils.common;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final LocalDate today = LocalDate.now();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter UTC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String pcdRequestDate() {
        LocalDate pastDate = today.minusDays(20);
        return pastDate.format(formatter);
    }

    public static String pcdDecisionByDate() {
        LocalDate futureDate = today.plusDays(27);
        return futureDate.format(formatter);
    }

    public static String UTCDateTimeNow() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(UTC_FORMATTER);
    }

    public static String UTCDateTimeInFutureDayBy(int noOfDays) {
        ZonedDateTime originalDate = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime newDate = originalDate.plusDays(noOfDays);
        return newDate.format(UTC_FORMATTER);
    }

    public static String UTCDateTimeInPastDayBy(int noOfDays) {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime newDate = now.minusDays(noOfDays);
        return now.format(UTC_FORMATTER);
    }





}

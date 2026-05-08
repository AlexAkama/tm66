package org.example.tm66.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter SHORT = DateTimeFormatter.ofPattern("dd.MM");


    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String now() {
        return LocalDateTime.now().format(DATE_TIME);
    }

    public static String date(LocalDate date) {
        return date.format(DATE);
    }

    public static String shortDate(LocalDate date) {
        return date.format(SHORT);
    }

}

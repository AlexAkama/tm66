package org.example.tm66.model;

import org.example.tm66.util.TimeUtils;

import java.time.LocalDate;

public record FinalizeComment(String orderId, LocalDate date, String comment) {

    public String getShortDate() {
        return TimeUtils.shortData(date);
    }

}

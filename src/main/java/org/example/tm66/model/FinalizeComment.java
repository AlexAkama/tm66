package org.example.tm66.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tm66.util.TimeUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinalizeComment {
    private String orderId;
    private LocalDate date;
    private String comment;

    public String getShortDate() {
        return TimeUtils.shortDate(date);
    }
}
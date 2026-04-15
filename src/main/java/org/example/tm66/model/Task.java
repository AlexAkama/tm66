package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Task {
    private LocalDate creatAt;
    private String orderId;
    private String url;
    private String orderStatus;
    private String city;
    private String location;
    private String address;
    private String chanel;
    private String point;
    private String work;
    private boolean isCargoWork;
    private boolean isTrashWork;
    private boolean isAuditWork;
    private boolean isRepairWork;
    private boolean isElectroWork;
    private Integer volume;
    private String equipment;
    private String comment;
    private LocalDate targetDate;
    private TaskStatus status;

    @Override
    public String toString() {
        return "\n" + work + " " + volume;
    }
}

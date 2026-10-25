package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderInfo extends IconIndicator {

    private LocalDate creatAt;
    private String orderId;
    private String url;
    private String orderStatus;
    private String city;
    private String location;
    private String address;
    private String author;
    private String contact;
    private String chanel;
    private String point;
    private String comment;
    private LocalDate targetDate;
    private TaskStatus status;
    private List<String> linkedOrder;

}

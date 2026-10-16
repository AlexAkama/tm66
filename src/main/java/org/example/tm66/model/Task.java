package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task extends IconIndicator{

    private OrderInfo orderInfo;
    private String work;
    private Integer volume;
    private String equipment;



    @Override
    public String toString() {
        return "\n" + work + " " + volume;
    }
}

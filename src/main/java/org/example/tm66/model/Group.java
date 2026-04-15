package org.example.tm66.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Group {

    private final List<Order> orders;

    public String getCity() {
        return orders.getFirst().getCity();
    }

    public String getLocation() {
        return orders.getFirst().getLocation();
    }

    @Override
    public String toString() {
        return "Город: " + getCity() + "\n" + orders;
     }

}

package org.example.tm66.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Group {

    private final List<Order> orders;

    public String getCity() {
        return orders.get(0).getCity();
    }

    public String getLocation() {
        return orders.get(0).getLocation();
    }

    @Override
    public String toString() {
        return "Город: " + getCity() + "\n" + orders;
     }

}

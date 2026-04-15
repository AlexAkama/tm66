package org.example.tm66.model;

import java.util.List;

public record TrashOrder(String orderId, List<TrashTask> tasks) {
}

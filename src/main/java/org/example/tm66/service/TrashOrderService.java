package org.example.tm66.service;

import org.example.tm66.model.TrashOrder;
import org.example.tm66.model.TrashTask;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TrashOrderService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path filePath = Paths.get("upload/trash_orders.json");

    public void update(List<String> lines) {
        TrashOrder order = map(lines);
        updateOrAddOrder(order);
    }

    private TrashOrder map(List<String> lines) {
        String orderId = lines.getFirst();
        List<TrashTask> tasks = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String s = lines.get(i);
            String[] split = s.split(" : x");
            int volume = Integer.parseInt(split[1].trim());
            String work = split[0].replace(";", "<br/>");
            TrashTask task = new TrashTask(work, volume);
            tasks.add(task);
        }
        return new TrashOrder(orderId, tasks);
    }

    private void updateOrAddOrder(TrashOrder order) {
        List<TrashOrder> orders = readOrdersFromFile();
        Optional<Integer> index = findOrderIndexById(orders, order.orderId());
        if (index.isPresent()) {
            orders.set(index.get(), order);
        } else {
            orders.add(order);
        }
        saveOrdersToFile(orders);
    }

    public Map<String, TrashOrder> getMapByOrderId() {
        return readOrdersFromFile().stream()
                .collect(Collectors.toMap(TrashOrder::orderId, Function.identity()));
    }

    private List<TrashOrder> readOrdersFromFile() {
        if (Files.exists(filePath)) {
            return objectMapper.readValue(filePath.toFile(), new TypeReference<>() {
            });
        } else {
            return new ArrayList<>();
        }
    }

    private Optional<Integer> findOrderIndexById(List<TrashOrder> orders, String orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).orderId().equals(orderId)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private void saveOrdersToFile(List<TrashOrder> orders) {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), orders);
    }

}

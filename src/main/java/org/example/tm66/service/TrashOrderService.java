package org.example.tm66.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tm66.config.UploadConfig;
import org.example.tm66.model.TrashOrder;
import org.example.tm66.model.TrashTask;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrashOrderService {

    private final ObjectMapper objectMapper;
    private final Path filePath;

    public TrashOrderService(ObjectMapper objectMapper, UploadConfig uploadConfig) {
        this.objectMapper = objectMapper;
        this.filePath = Paths.get(uploadConfig.getCommentDir() + "/trash_orders.json");
    }

    public String update(List<String> lines) throws IOException {
        TrashOrder order = map(lines);
        return updateOrAddOrder(order);
    }

    private TrashOrder map(List<String> lines) {
        String orderId = lines.get(0);
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

    private String updateOrAddOrder(TrashOrder order) throws IOException {
        List<TrashOrder> orders = readOrdersFromFile();
        Optional<Integer> index = findOrderIndexById(orders, order.getOrderId());
        if (index.isPresent()) {
            orders.set(index.get(), order);
        } else {
            orders.add(order);
        }
        saveOrdersToFile(orders);
        return order.getOrderId();
    }

    public Map<String, List<TrashTask>> getMapByOrderId() throws IOException {
        return readOrdersFromFile().stream()
                .collect(Collectors.toMap(TrashOrder::getOrderId, TrashOrder::getTasks));
    }

    /**
     * Очистка не используемых расшифровок утилизаций.
     *
     * @param usedIds список используемых id
     */
    public void clear(Set<String> usedIds) throws IOException {
        List<TrashOrder> orders = readOrdersFromFile();
        List<TrashOrder> filtered = orders.stream()
                .filter(order -> usedIds.contains(order.getOrderId()))
                .collect(Collectors.toList());
        saveOrdersToFile(filtered);
    }

    private List<TrashOrder> readOrdersFromFile() throws IOException {
        if (Files.exists(filePath)) {
            return objectMapper.readValue(filePath.toFile(), new TypeReference<List<TrashOrder>>() {
            });
        } else {
            return new ArrayList<>();
        }
    }

    private Optional<Integer> findOrderIndexById(List<TrashOrder> orders, String orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(orderId)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private void saveOrdersToFile(List<TrashOrder> orders) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), orders);
    }

}

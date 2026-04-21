package org.example.tm66.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.tm66.config.KgsUserParams;
import org.example.tm66.mapper.TaskMapper;
import org.example.tm66.model.*;
import org.example.tm66.processor.ParseProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class OrderService {

    private final TrashOrderService trashOrderService;
    private final FinalizeCommentService finalizeCommentService;
    private final KgsUserParams kgsUserParams;

    private Workbook workbook;
    private List<Group> groups;

    public void process(Workbook w) {
        this.workbook = w;
        process();
    }

    public void process() {
        if (this.workbook == null) return;

        Sheet sheet = this.workbook.getSheetAt(0);

        ParseProcessor parser = new ParseProcessor();
        parser.init(sheet.getRow(0));
        List<RowDto> parsed = parser.parse(sheet);

        TaskMapper.init();
        List<Task> tasks = TaskMapper.map(parsed);

        Map<String, List<Task>> orderContent = tasks.stream()
                .collect(Collectors.groupingBy(Task::getOrderId));

        List<Order> orders = orderContent.values()
                .stream()
                .map(t -> new Order(t, kgsUserParams.getEmail()))
                .sorted(Comparator.comparing(Order::getTargetDate))
                .toList();

        Map<String, TrashOrder> trashMap = trashOrderService.getMapByOrderId();
        Map<String, List<FinalizeComment>> commentMap = finalizeCommentService.getMapByOrderId();
        for (Order order : orders) {
            String orderId = order.getOrderId();
            if (trashMap.containsKey(orderId)) {
                TrashOrder trashOrder = trashMap.get(orderId);
                order.setTrashTasks(trashOrder.tasks());
            }
            if (commentMap.containsKey(orderId)) {
                List<FinalizeComment> comments = commentMap.get(orderId);
                order.setFinalizeComments(comments);
            }
        }

        Map<String, List<Order>> groupContent = orders.stream()
                .collect(Collectors.groupingBy(Order::getCity));

        groups = groupContent.values()
                .stream()
                .map(Group::new)
                .sorted(Comparator.comparing(Group::getLocation))
                .toList();
    }

    public List<String> getNowEndOrderId() {
        if (groups == null) return null;
        LocalDate now = LocalDate.now();
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() != TaskStatus.READY)
                .filter(order -> order.getTargetDate().equals(now))
                .map(Order::getOrderId)
                .toList();
    }

    public List<String> getReturnedOrderId() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() == TaskStatus.RETURNED)
                .map(Order::getOrderId)
                .toList();
    }

    public Map<String, String> getReturnedLinkMap() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() == TaskStatus.RETURNED)
                .collect(Collectors.toMap(Order::getOrderId, Order::getUrl));
    }

    public Map<String, String> getTrashLinkMap() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(Order::isTrash)
                .collect(Collectors.toMap(Order::getOrderId, Order::getUrl));
    }

}

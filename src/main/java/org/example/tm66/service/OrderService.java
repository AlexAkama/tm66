package org.example.tm66.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.tm66.config.KgsUserParams;
import org.example.tm66.mapper.TaskMapper;
import org.example.tm66.model.FinalizeComment;
import org.example.tm66.model.Group;
import org.example.tm66.model.Order;
import org.example.tm66.model.RowDto;
import org.example.tm66.model.Task;
import org.example.tm66.model.TaskStatus;
import org.example.tm66.model.TrashTask;
import org.example.tm66.processor.ParseProcessor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.tm66.model.TaskStatus.RETURNED;

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

    public void process(Workbook w) throws IOException {
        this.workbook = w;
        process();
    }

    public void process() throws IOException {
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
                .collect(Collectors.toList());

        Map<String, List<TrashTask>> trashMap = trashOrderService.getMapByOrderId();
        Map<String, List<FinalizeComment>> commentMap = finalizeCommentService.getMapByOrderId();
        for (Order order : orders) {
            String orderId = order.getOrderId();
            if (trashMap.containsKey(orderId)) {
                order.setTrashTasks(trashMap.get(orderId));
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
                .collect(Collectors.toList());
    }

    public List<String> getNowEndOrderId() {
        if (groups == null) return null;
        LocalDate now = LocalDate.now();
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() != TaskStatus.READY)
                .filter(order -> order.getTargetDate().equals(now))
                .map(Order::getOrderId)
                .collect(Collectors.toList());
    }

    public List<String> getReturnedOrderId() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() == RETURNED)
                .map(Order::getOrderId)
                .collect(Collectors.toList());
    }

    public Map<String, String> getReturnedLinkMap() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> order.getStatus() == RETURNED)
                .collect(Collectors.toMap(Order::getOrderId, Order::getUrl));
    }

    public Map<String, String> getTrashLinkMap() {
        if (groups == null) return null;
        return groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(Order::isTrash)
                .collect(Collectors.toMap(Order::getOrderId, Order::getUrl));
    }

    public void clearTrash() throws IOException {
        Set<String> usedIds = groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(Order::isTrash)
                .map(Order::getOrderId)
                .collect(Collectors.toSet());
        trashOrderService.clear(usedIds);
    }

    public void clearComment() throws IOException {
        Set<String> usedIds = groups.stream()
                .flatMap(group -> group.getOrders().stream())
                .filter(order -> RETURNED == order.getStatus())
                .map(Order::getOrderId)
                .collect(Collectors.toSet());
        finalizeCommentService.clear(usedIds);
    }

}

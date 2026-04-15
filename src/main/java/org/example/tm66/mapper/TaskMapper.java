package org.example.tm66.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.tm66.model.RowDto;
import org.example.tm66.model.Task;
import org.example.tm66.model.TaskStatus;
import org.example.tm66.processor.WorkValidator;
import org.example.tm66.processor.Normalizer;
import org.example.tm66.processor.Locations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskMapper {

    public static final String BASE_CITY = "Нижний Тагил";
    public static final String WAREHOUSE = "Склад";
    public static final String BASE_ADDRESS = "Восточное шоссе 16д";


    private static final Map<String, String> COMMENT_MAP = new HashMap<>();

    private static final Set<String> FILTERED_WORKS = Set.of(
            "Легковой выезд в тт",
            "Грузовой выезд в тт (монтаж/демонтаж)"
    );

    public static void init() {
        COMMENT_MAP.clear();
    }

    public static List<Task> map(List<RowDto> rows) {
        return rows.stream()
                .filter(row -> row.getVolume() != null)
                .filter(row -> row.getVolume() > 0)
                .filter(row -> !FILTERED_WORKS.contains(row.getWorkGroup()))
                .map(TaskMapper::map)
                .toList();
    }

    public static Task map(RowDto row) {
        Task task = new Task();
        task.setCreatAt(row.getCreateDate());

        normalize(row);
        enrich(row);
        trimTrash(row);
        separateWarehouse(row);

        task.setOrderId(row.getOrderId());
        task.setUrl(row.getOrderLink());

        task.setCity(row.getCity());
        task.setLocation(Locations.getLocation(row.getCity()));

        String workGroup = row.getWorkGroup();
        task.setWork(isNotBlank(workGroup) ? workGroup : row.getOrderType());

        task.setCargoWork(WorkValidator.isCargo(workGroup));
        task.setTrashWork(WorkValidator.isTrash(workGroup));
        task.setAuditWork(WorkValidator.isAudit(workGroup));
        task.setRepairWork(WorkValidator.isRepair(workGroup));
        task.setElectroWork(WorkValidator.isElectro(workGroup));

        task.setVolume(row.getVolume());

        String equipment = row.getEquipment();
        task.setEquipment(isNotBlank(equipment) ? equipment : "оборудование не указано");

        String address = Normalizer.normalizeAddress(row.getAddress(), task.getCity());
        task.setAddress(address);

        task.setTargetDate(row.getTargetDate());

        task.setComment(COMMENT_MAP.getOrDefault(row.getOrderId(), buildAndSaveComment(row)));

        task.setChanel(row.getChannel());

        String point = Normalizer.normalizePoint(row.getPoint());
        task.setPoint(point);

        task.setStatus(TaskStatus.get(row.getOrderStatus()));

        return task;
    }

    private static void normalize(RowDto row) {
        String normalize = Normalizer.normalizeCity(row.getCity());
        row.setCity(normalize);
    }

    private static void enrich(RowDto row) {
        String city = row.getCity();
        String address = row.getAddress();
        if (isBlank(city) && isBlank(address)) {
            row.setCity(BASE_CITY);
            row.setAddress(BASE_ADDRESS);
        } else if (BASE_CITY.equals(city) && isBlank(address)) {
            row.setAddress(BASE_ADDRESS);
        }
    }

    private static void trimTrash(RowDto row) {
        String city = row.getCity();
        String address = row.getAddress();
        while (address.startsWith(city)) {
            char c = address.charAt(city.length());
            if (c != ' ' && c != ',') break;
            int i = address.indexOf(' ', city.length());
            address = address.substring(i + 1);
            row.setAddress(address);
        }

        String orderId = row.getOrderId();
        int i = orderId.indexOf('.');
        if (i > -1) {
            row.setOrderId(orderId.substring(0, i));
        }

    }

    private static void separateWarehouse(RowDto row) {
        String city = row.getCity();
        String address = row.getAddress();
        if (BASE_CITY.equals(city) && BASE_ADDRESS.equals(address)) {
            row.setCity(WAREHOUSE);
            row.setAddress(WAREHOUSE);
        }
    }

    private static String buildAndSaveComment(RowDto row) {
        String lineSeparator = "<br/>";
        String initComment = row.getInitComment();
        if (initComment == null || initComment.length() == 1) initComment = "";
        else initComment = initComment.replace("; ", "");

        StringBuilder sb = new StringBuilder();

        if (isNotBlank(initComment)) sb.append("ИНИЦИАТОР:").append(lineSeparator).append(initComment);

        String jtiComment = row.getJtiComment();
        boolean jtiCommentNotBlank = isNotBlank(jtiComment);
        if (jtiCommentNotBlank) jtiComment = "JTI:" + lineSeparator + jtiComment;

        if (jtiCommentNotBlank && !sb.isEmpty()) sb.append(lineSeparator);
        sb.append(jtiComment);

        String executorComment = row.getExecutorComment();
        boolean executorCommentNotBlank = isNotBlank(executorComment);
        if (executorCommentNotBlank) executorComment = "ИНФОРМАЦИЯ:" + lineSeparator + executorComment;

        if (executorCommentNotBlank && !sb.isEmpty()) sb.append(lineSeparator);
        sb.append(executorComment);

        String comment = sb.toString();

        comment = comment.replaceAll("\"\"", "");

        if (isBlank(comment)) comment = null;

        COMMENT_MAP.put(row.getOrderId(), comment);
        return comment;
    }

}

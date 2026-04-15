package org.example.tm66.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.tm66.processor.Normalizer;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.example.tm66.mapper.TaskMapper.WAREHOUSE;

@Getter
@RequiredArgsConstructor
public class Order {

    private final List<Task> tasks;
    private final String executorEmail;

    @Setter
    private List<TrashTask> trashTasks;

    @Setter
    private List<FinalizeComment> finalizeComments;

    public LocalDate getCreatAt() {
        return tasks.getFirst().getCreatAt();
    }

    public String getOrderId() {
        return tasks.getFirst().getOrderId();
    }

    public String getUrl() {
        return tasks.getFirst().getUrl();
    }

    public String getCity() {
        return tasks.getFirst().getCity();
    }

    public String getLocation() {
        return tasks.getFirst().getLocation();
    }

    public String getAddress() {
        return tasks.getFirst().getAddress();
    }

    public String getChanel() {
        return tasks.getFirst().getChanel();
    }

    public String getPoint() {
        return tasks.getFirst().getPoint();
    }

    public String getComment() {
        return tasks.getFirst().getComment();
    }

    public LocalDate getTargetDate() {
        return tasks.getFirst().getTargetDate();
    }

    public String getTargetDateCss() {
        if (getStatus() != TaskStatus.READY) {
            LocalDate now = LocalDate.now();
            int i = now.compareTo(getTargetDate());
            if (i > 0) return "red-mark";
            if (i == 0) return "red";
        }
        return "";

    }

    public TaskStatus getStatus() {
        return tasks.getFirst().getStatus();
    }

    public boolean isCargo() {
        return hasTaskMatching(Task::isCargoWork);
    }

    public boolean isTrash() {
        return hasTaskMatching(Task::isTrashWork);
    }

    public boolean isAudit() {
        return hasTaskMatching(Task::isAuditWork);
    }

    public boolean isRepair() {
        return hasTaskMatching(Task::isRepairWork);
    }

    public boolean isElectro() {
        return hasTaskMatching(Task::isElectroWork);
    }

    public String getMailToUrl() {
        String text = "ОТЧЕТ" + " "
                + getOrderId() + " "
                + getCity().toUpperCase() + " "
                + getUrlAddress(tasks.getFirst());
        text = text.replaceAll("\\s+", "%20");
        return "mailto:" + executorEmail + "?subject=" + text;
    }

    private String getUrlAddress(Task task) {
        if (WAREHOUSE.equals(task.getCity())) return "";
        String address = Normalizer.normalizeAddressToMail(task.getAddress());
        String point = Normalizer.normalizePointToMail(task.getPoint());
        String text = address + " " + point;
        return truncateAtSpace(text, 15);
    }

    private static String truncateAtSpace(String text, int limit) {
        if (text == null || text.length() <= limit) {
            return text;
        }
        int spaceIndex = text.indexOf(' ', limit);
        if (spaceIndex != -1) {
            return text.substring(0, spaceIndex);
        }
        return text;
    }

    @Override
    public String toString() {
        return String.format("\n%s %s %d %s", getCreatAt(), getOrderId(), tasks.size(), tasks);
    }

    private boolean hasTaskMatching(Predicate<Task> condition) {
        for (Task task : tasks) {
            if (condition.test(task)) return true;
        }
        return false;
    }

}

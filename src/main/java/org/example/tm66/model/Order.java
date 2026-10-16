package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;
import org.example.tm66.processor.Normalizer;
import org.example.tm66.util.TimeUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import static org.example.tm66.mapper.TaskMapper.WAREHOUSE;

@Getter
public class Order {

    private final OrderInfo info;
    private final List<Task> tasks;
    private final String executorEmail;

    @Setter
    private List<TrashTask> trashTasks;

    @Setter
    private List<FinalizeComment> finalizeComments;

    public Order(List<Task> tasks, String executorEmail) {
        this.info = tasks.get(0).getOrderInfo();
        this.tasks = tasks;
        this.executorEmail = executorEmail;

        this.info.setCargoWork(hasTaskMatching(Task::isCargoWork));
        this.info.setTrashWork(hasTaskMatching(Task::isTrashWork));
        this.info.setAuditWork(hasTaskMatching(Task::isAuditWork));
        this.info.setRepairWork(hasTaskMatching(Task::isRepairWork));
        this.info.setElectroWork(hasTaskMatching(Task::isElectroWork));
        this.info.setRecoveryWork(hasTaskMatching(Task::isRecoveryWork));
        this.info.setReceivingWork(hasTaskMatching(Task::isReceivingWork));
        this.info.setIssuingWork(hasTaskMatching(Task::isIssuingWork));

    }

    public LocalDate getCreatAt() {
        return info.getCreatAt();
    }

    public String getOrderId() {
        return info.getOrderId();
    }

    public String getUrl() {
        return info.getUrl();
    }

    public String getCity() {
        return info.getCity();
    }

    public String getLocation() {
        return info.getLocation();
    }

    public String getAddress() {
        return info.getAddress();
    }

    public String getContact() {
        return info.getContact();
    }

    public String getChanel() {
        return info.getChanel();
    }

    public String getPoint() {
        return info.getPoint();
    }

    public String getComment() {
        return info.getComment();
    }

    public List<String> getLinkedOrder() {
        return info.getLinkedOrder();
    }

    public LocalDate getTargetDate() {
        return info.getTargetDate();
    }

    public String getTargetDateString() {
        return TimeUtils.date(getTargetDate());
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
        return info.getStatus();
    }

    public boolean isCargo() {
        return info.isCargoWork();
    }

    public boolean isTrash() {
        return info.isTrashWork();
    }

    public boolean isAudit() {
        return info.isAuditWork();
    }

    public boolean isRepair() {
        return info.isRepairWork();
    }

    public boolean isElectro() {
        return info.isElectroWork();
    }

    public boolean isRecovery() {
        return info.isRecoveryWork();
    }

    public boolean isReceiving() {
        return info.isReceivingWork();
    }

    public boolean isIssuing() {
        return info.isIssuingWork();
    }

    public String getMailToUrl() {
        String text = "ОТЧЕТ" + " "
                + getOrderId() + " "
                + getCity().toUpperCase() + " "
                + getUrlAddress(info);
        text = text.replaceAll("\\s+", "%20");
        return "mailto:" + executorEmail + "?subject=" + text;
    }

    private String getUrlAddress(OrderInfo info) {
        if (WAREHOUSE.equals(info.getCity())) return "";
        String address = Normalizer.normalizeAddressToMail(info.getAddress());
        String point = Normalizer.normalizePointToMail(info.getPoint());
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

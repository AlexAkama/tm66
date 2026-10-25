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

    private final OrderInfo orderInfo;
    private final List<Task> tasks;
    private final String executorEmail;

    @Setter
    private List<TrashTask> trashTasks;

    @Setter
    private List<FinalizeComment> finalizeComments;

    public Order(List<Task> tasks, String executorEmail) {
        this.orderInfo = tasks.get(0).getOrderInfo();
        this.tasks = tasks;
        this.executorEmail = executorEmail;

        this.orderInfo.setCargoWork(hasTaskMatching(Task::isCargoWork));
        this.orderInfo.setTrashWork(hasTaskMatching(Task::isTrashWork));
        this.orderInfo.setAuditWork(hasTaskMatching(Task::isAuditWork));
        this.orderInfo.setRepairWork(hasTaskMatching(Task::isRepairWork));
        this.orderInfo.setElectroWork(hasTaskMatching(Task::isElectroWork));
        this.orderInfo.setRecoveryWork(hasTaskMatching(Task::isRecoveryWork));
        this.orderInfo.setReceivingWork(hasTaskMatching(Task::isReceivingWork));
        this.orderInfo.setIssuingWork(hasTaskMatching(Task::isIssuingWork));

    }

    public LocalDate getCreatAt() {
        return orderInfo.getCreatAt();
    }

    public String getOrderId() {
        return orderInfo.getOrderId();
    }

    public String getUrl() {
        return orderInfo.getUrl();
    }

    public String getCity() {
        return orderInfo.getCity();
    }

    public String getLocation() {
        return orderInfo.getLocation();
    }

    public String getAddress() {
        return orderInfo.getAddress();
    }

    public String getContact() {
        return orderInfo.getContact();
    }

    public String getChanel() {
        return orderInfo.getChanel();
    }

    public String getPoint() {
        return orderInfo.getPoint();
    }

    public String getComment() {
        return orderInfo.getComment();
    }

    public List<String> getLinkedOrder() {
        return orderInfo.getLinkedOrder();
    }

    public LocalDate getTargetDate() {
        return orderInfo.getTargetDate();
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
        return orderInfo.getStatus();
    }

    public boolean isCargo() {
        return orderInfo.isCargoWork();
    }

    public boolean isTrash() {
        return orderInfo.isTrashWork();
    }

    public boolean isAudit() {
        return orderInfo.isAuditWork();
    }

    public boolean isRepair() {
        return orderInfo.isRepairWork();
    }

    public boolean isElectro() {
        return orderInfo.isElectroWork();
    }

    public boolean isRecovery() {
        return orderInfo.isRecoveryWork();
    }

    public boolean isReceiving() {
        return orderInfo.isReceivingWork();
    }

    public boolean isIssuing() {
        return orderInfo.isIssuingWork();
    }

    public String getMailToUrl() {
        String text = "ОТЧЕТ" + " "
                + getOrderId() + " "
                + getCity().toUpperCase() + " "
                + getUrlAddress(orderInfo);
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

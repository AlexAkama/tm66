package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Cell;
import org.example.tm66.util.TimeUtils;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RowDto {
    private String orderType;
    private String channel;
    private String city;
    private LocalDate createDate;
    private LocalDate targetDate;
    private String orderId;
    private String point;
    private String address;
    private String workGroup;
    private Integer volume;
    private String jtiComment;
    private String executorComment;
    private String initComment;
    private String orderStatus;
    private String orderLink;
    private String equipment;

    public void set(Headers field, Cell cell) {
        if (cell == null) return;
        try {
            switch (field) {
                case ORDER_TYPE:
                    this.orderType = cell.getStringCellValue().trim();
                    break;
                case CHANNEL:
                    this.channel = cell.getStringCellValue().trim();
                    break;
                case CITY:
                    this.city = cell.getStringCellValue().trim();
                    break;
                case CREATE_DATE:
                    this.createDate = TimeUtils.toLocalDate(cell.getDateCellValue());
                    break;
                case TARGET_DATE:
                    this.targetDate = TimeUtils.toLocalDate(cell.getDateCellValue());
                    break;
                case ORDER_ID:
                    this.orderId = cell.toString();
                    break;
                case POINT:
                    this.point = cell.getStringCellValue().trim();
                    break;
                case ADDRESS:
                    this.address = cell.getStringCellValue().trim();
                    break;
                case WORK_GROUP:
                    this.workGroup = cell.getStringCellValue().trim();
                    break;
                case VOLUME:
                    this.volume = (int) cell.getNumericCellValue();
                    break;
                case JTI_COMMENT:
                    this.jtiComment = cell.getStringCellValue().trim();
                    break;
                case EXECUTOR_COMMENT:
                    this.executorComment = cell.getStringCellValue().trim();
                    break;
                case INIT_COMMENT:
                    this.initComment = cell.getStringCellValue().trim();
                    break;
                case ORDER_STATUS:
                    this.orderStatus = cell.getStringCellValue().trim();
                    break;
                case ORDER_LINK:
                    this.orderLink = cell.getStringCellValue().trim();
                    break;
                case EQUIPMENT:
                    this.equipment = cell.getStringCellValue().trim();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(field.getText() + ": '" + cell + "'");
        }
    }

}

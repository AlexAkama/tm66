package org.example.tm66.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconIndicator {

    private boolean isCargoWork;
    private boolean isTrashWork;
    private boolean isAuditWork;
    private boolean isRepairWork;
    private boolean isElectroWork;
    private boolean isRecoveryWork;
    private boolean isReceivingWork;
    private boolean isIssuingWork;

}

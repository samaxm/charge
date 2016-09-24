package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.ConsumeType;

/**
 * Created by Sammax on 2016/9/9.
 */
public class SingleChargeEvent implements ChargeEvent {

    private ConsumeType type;
    private String dwID;
    private ChargeOperation operation;

    public SingleChargeEvent(ConsumeType type, String dwID,ChargeOperation operation) {
        this.type = type;
        this.dwID = dwID;
        this.operation=operation;
    }

    public ChargeOperation getOperation() {
        return operation;
    }

    public void setOperation(ChargeOperation operation) {
        this.operation = operation;
    }

    public SingleChargeEvent(){}


    public String getDwID() {
        return dwID;
    }

    public void setDwID(String dwID) {
        this.dwID = dwID;
    }

    @Override
    public ConsumeType getConsumeType() {
        return type;
    }
}

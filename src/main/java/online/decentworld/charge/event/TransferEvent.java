package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/11/7.
 */
public class TransferEvent extends SingleChargeEvent {

    private String orderNum;
    private int amount;

    public TransferEvent(String dwID,int amount, String orderNum) {
        super(MutableConsumeType.RECHARGE, dwID, ChargeOperation.PLUS);
        this.orderNum = orderNum;
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}

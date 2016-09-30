package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/9/24.
 */
public class RechargeEvent extends SingleChargeEvent {

    private String orderNum;
    private int amount;

    public RechargeEvent(String dwID,int amount, String orderNum) {
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

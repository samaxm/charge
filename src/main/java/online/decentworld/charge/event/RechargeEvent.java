package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/9/24.
 */
public class RechargeEvent extends SingleChargeEvent {
    private String orderNum;
    public RechargeEvent(String orderNum,String dwID){
        new SingleChargeEvent(MutableConsumeType.RECHARGE,dwID, ChargeOperation.PLUS);
        this.orderNum=orderNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}

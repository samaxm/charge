package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/11/16.
 */
public class TipEvent extends P2PChargeEvent {

    private  int amount;

    public TipEvent(String dwID, String tipedUserID,int amount) {
        super(dwID, tipedUserID, ChargeOperation.MINUS, ChargeOperation.PLUS, MutableConsumeType.TIP);
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }
}

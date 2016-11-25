package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/11/25.
 */
public class ChangeWorthEvent extends SingleChargeEvent {

    public int getWorth() {
        return worth;
    }

    private int worth;


    public ChangeWorthEvent(int worth,String dwID) {
        super(MutableConsumeType.CHANGE_WORTH, dwID, ChargeOperation.MINUS);
        this.worth=worth;
    }



}

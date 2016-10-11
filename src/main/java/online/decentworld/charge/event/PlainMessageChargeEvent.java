package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/10/11.
 */
public class PlainMessageChargeEvent extends P2PChargeEvent {

    public PlainMessageChargeEvent(String payer, String payee) {
        super(payer, payee, ChargeOperation.MINUS,ChargeOperation.PLUS, MutableConsumeType.PLAINCHAT);
    }
}

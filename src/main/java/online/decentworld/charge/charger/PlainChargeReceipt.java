package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeReceipt;

/**
 * Created by Sammax on 2016/10/7.
 */
public class PlainChargeReceipt implements ChargeReceipt {

    private ChargeResult result;

    public PlainChargeReceipt(ChargeResult result){
        this.result=result;
    }


    @Override
    public ChargeResult getChargeResult() {
        return result;
    }
}

package online.decentworld.charge.receipt;

import online.decentworld.charge.charger.ChargeResult;

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

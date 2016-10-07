package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeReceipt;
import online.decentworld.charge.ChargeReceiptWrapper;
import online.decentworld.charge.price.PriceCountResult;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/10/7.
 */
public class DefaultChargeReceiptWrapper implements ChargeReceiptWrapper {


    @Override
    public ChargeReceipt wrapChargeResult(ChargeResult result, PriceCountResult price) {
        if(result.getType()== MutableConsumeType.RECHARGE){
            return new PlainChargeReceipt(result);
        }else{
            return null;
        }
    }
}

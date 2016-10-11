package online.decentworld.charge.receipt;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.price.PriceCountResult;

/**
 * Created by Sammax on 2016/9/24.
 */
public interface ChargeReceiptWrapper {
    public ChargeReceipt wrapChargeResult(ChargeResult result,PriceCountResult price);
}

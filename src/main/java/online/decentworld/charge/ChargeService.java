package online.decentworld.charge;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.interceptor.ChargeInterceptor;
import online.decentworld.charge.receipt.ChargeReceipt;
import online.decentworld.charge.receipt.ChargeReceiptWrapper;

/**
 * Created by Sammax on 2016/9/22.
 */
public interface ChargeService {

    public ChargeReceipt charge(ChargeEvent event) throws IllegalChargeException;
    public ChargeInterceptor addInterceptor(ChargeInterceptor interceptor);
    public void setChargerFactory(ChargerFactory factory);
    public void setPriceCounterFactory(PriceCounterFactory priceCounterFactory);
    public void setChargeReceiptWrapper(ChargeReceiptWrapper wrapper);
}

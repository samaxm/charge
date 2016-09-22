package online.decentworld.charge.interceptor;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/22.
 */
public interface ChargeInterceptor {
    void beforeCharge(ChargeEvent event) throws IllegalChargeException;
    void afterCharge(ChargeResult result) throws IllegalChargeException;
    ChargeInterceptor then(ChargeInterceptor next);
}

package online.decentworld.charge.interceptor;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.type.ConsumeType;

/**
 * Created by Sammax on 2016/9/22.
 */
public interface ChargeInterceptor {
    void beforeCharge(ChargeEvent event) throws IllegalChargeException;
    void afterCharge(ChargeResult result) throws IllegalChargeException;
    boolean accept(ConsumeType type);
    ChargeInterceptor then(ChargeInterceptor next);
}

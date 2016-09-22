package online.decentworld.charge;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.interceptor.IllegalChargeException;

/**
 * Created by Sammax on 2016/9/22.
 */
public interface ChargeService {

    public ChargeResult charge(ChargeEvent event) throws IllegalChargeException;

}

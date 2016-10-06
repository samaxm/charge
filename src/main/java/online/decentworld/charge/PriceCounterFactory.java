package online.decentworld.charge;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.price.PriceCounter;

/**
 * Created by Sammax on 2016/9/24.
 */
public interface PriceCounterFactory {
    PriceCounter getPriceCounter(ChargeEvent event) throws IllegalChargeException;
}

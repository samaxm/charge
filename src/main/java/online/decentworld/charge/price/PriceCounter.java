package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;

/**
 * Created by Sammax on 2016/9/9.
 */

public interface PriceCounter {
    public PriceCountResult getPrice(ChargeEvent event) throws IllegalChargeException;
}

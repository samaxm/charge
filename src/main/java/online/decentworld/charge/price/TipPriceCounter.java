package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.TipEvent;
import online.decentworld.charge.exception.IllegalChargeException;

/**
 * Created by Sammax on 2016/11/16.
 */
public class TipPriceCounter implements PriceCounter {


    @Override
    public PriceCountResult getPrice(ChargeEvent event) throws IllegalChargeException {
        TipEvent tipEvent=(TipEvent)event;
        return new P2PPriceCountResult(tipEvent.getAmount(),tipEvent.getAmount());
    }

}

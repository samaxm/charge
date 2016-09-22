package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/12.
 */
public class DefaultPriceCounter implements PriceCounter{


    @Override
    public P2PPriceCountResult getP2PCost(ChargeEvent event) {
        return null;
    }

    @Override
    public PriceCountResult getCost(ChargeEvent event) {
        return null;
    }
}

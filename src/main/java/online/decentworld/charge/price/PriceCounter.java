package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/9.
 */

public interface PriceCounter {
    public P2PPriceCountResult getP2PCost(ChargeEvent event);
    public PriceCountResult getCost(ChargeEvent event);
}

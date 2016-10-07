package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;

/**
 * Created by Sammax on 2016/9/23.
 */
public class PlainMessagePriceCounter implements PriceCounter {


    @Override
    public PriceCountResult getPrice(ChargeEvent event) {
        if(event instanceof P2PChargeEvent){
            return new P2PPriceCountResult(-1,1);
        }
        return null;
    }



}

package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/23.
 */
public class MessagePriceCounter implements PriceCounter {


    @Override
    public PriceCountResult getPrice(ChargeEvent event) {
//        if(event instanceof P2PChargeEvent){
//            P2PChargeEvent p2PChargeEvent=(P2PChargeEvent) event;
//            new P2PPriceCountResult(p2PChargeEvent.getPayer(),p2PChargeEvent.getConsumType(),
//                    -1,1,p2PChargeEvent.getPayee());
//        }
        return null;
    }



}

package online.decentworld.charge.charger;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import online.decentworld.charge.event.SingleChargeEvent;
import online.decentworld.charge.price.P2PPriceCountResult;
import online.decentworld.charge.price.PriceCountResult;

/**
 * Created by Sammax on 2016/9/24.
 */
public class DefalutCharger implements  ICharger {

    private DBCharger charger=new DBCharger();

    @Override
    public ChargeResult charge(ChargeEvent event,PriceCountResult price) {
        if(event instanceof P2PChargeEvent){
            P2PPriceCountResult result=(P2PPriceCountResult)price;
            P2PChargeEvent p2PChargeEvent=(P2PChargeEvent)event;
            return charger.p2pCharge(p2PChargeEvent.getPayer(), p2PChargeEvent.getPayee()
                    ,p2PChargeEvent.getPayerChargeOperation().getChargePrice(result.getPayerChargeAmount()),
                    p2PChargeEvent.getPayeeChargeOperation().getChargePrice(result.getPayeeChargeAmount()));
        }else{
            SingleChargeEvent singleChargeEvent=(SingleChargeEvent)event;
            return charger.charge(singleChargeEvent.getDwID(),
                    singleChargeEvent.getOperation().getChargePrice(price.getPayerChargeAmount()));
        }
    }
}

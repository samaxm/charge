package online.decentworld.charge;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.charger.ICharger;
import online.decentworld.charge.charger.P2PChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import online.decentworld.charge.interceptor.ChargeInterceptor;
import online.decentworld.charge.interceptor.IllegalChargeException;
import online.decentworld.charge.price.P2PPriceCountResult;
import online.decentworld.charge.price.PriceCounter;

/**
 * Created by Sammax on 2016/9/22.
 */
public abstract class ChargeServiceTemplate implements ChargeService {


    @Override
    public P2PChargeResult p2pCharge(P2PChargeEvent event) throws IllegalChargeException {
        getChargeInterceptors(event).beforeCharge(event);
        P2PPriceCountResult price=(P2PPriceCountResult)getPriceCounter(event).;
        P2PChargeResult result=getCharger(event).p2pCharge(price.getPayer(), price.getPayee()
                , price.getPayerChargeAmount(), price.getPayeeChargeAmount());
        getChargeInterceptors(event).afterCharge(result);
        return null;
    }

    @Override
    public ChargeResult charge(ChargeEvent event) throws IllegalChargeException {
        getChargeInterceptors(event).beforeCharge(event);
        PriceCounter priceCounter=getPriceCounter(event);
        ICharger charger=getCharger(event);
        charger.charge()

        return null;
    }

    abstract protected PriceCounter getPriceCounter(ChargeEvent event);

    abstract protected ChargeInterceptor getChargeInterceptors(ChargeEvent event);

    abstract protected ICharger getCharger(ChargeEvent event);

}

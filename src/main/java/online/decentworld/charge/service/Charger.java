package online.decentworld.charge.service;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.charger.ICharger;
import online.decentworld.charge.charger.P2PChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import online.decentworld.charge.price.P2PPriceCountResult;
import online.decentworld.charge.price.PriceCountResult;
import online.decentworld.charge.price.PriceCounter;

/**
 * Created by Sammax on 2016/9/9.
 */

public class Charger {

    private ICharger chargeService;

    private PriceCounter priceCounter;

    public P2PChargeResult p2pCharge(P2PChargeEvent event){
        P2PPriceCountResult price= priceCounter.getP2PCost(event);
        P2PChargeResult result=chargeService.p2pCharge(event.getPayer(), event.getPayee(), price.getPayerChargeAmount(), price.getPayeeChargeAmount());
        return result;
    }

    public ChargeResult charge(ChargeEvent event){
        PriceCountResult price=priceCounter.getCost(event);
        ChargeResult result=chargeService.charge(price.getDwID(),price.getCost());
        result.setType(price.getType());
        return result;
    }


    public Charger(ICharger chargeService, PriceCounter priceCounter) {
        this.chargeService = chargeService;
        this.priceCounter = priceCounter;
    }
}

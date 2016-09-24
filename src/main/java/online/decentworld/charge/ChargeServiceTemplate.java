package online.decentworld.charge;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.charger.ICharger;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.interceptor.ChargeInterceptor;
import online.decentworld.charge.price.PriceCountResult;
import online.decentworld.charge.price.PriceCounter;

/**
* Created by Sammax on 2016/9/22.
*/
public class ChargeServiceTemplate implements ChargeService {

    private ChargerFactory chargerFactory;
    private PriceCounterFactory priceCounterFactory;
    private ChargeInterceptor headInterceptor;
    private ChargeReceiptWrapper wrapper;

    @Override
    public ChargeReceipt charge(ChargeEvent event) throws IllegalChargeException {
        headInterceptor.beforeCharge(event);
        PriceCounter counter=priceCounterFactory.getPriceCounter(event);
        PriceCountResult price=counter.getPrice(event);
        ICharger charger=chargerFactory.getCharger(event);
        ChargeResult chargeResult=charger.charge(event,price);
        headInterceptor.afterCharge(chargeResult);
        return wrapper.wrapChargeResult(chargeResult,price);
    }

    @Override
    public ChargeInterceptor setInterceptor(ChargeInterceptor interceptor) {
        this.headInterceptor=interceptor;
        return interceptor;
    }

    @Override
    public void setChargerFactory(ChargerFactory factory) {
        this.chargerFactory=factory;
    }

    @Override
    public void setPriceCounterFactory(PriceCounterFactory priceCounterFactory) {
        this.priceCounterFactory=priceCounterFactory;
    }
    @Override
    public void setChargeReceiptWrapper(ChargeReceiptWrapper wrapper) {
        this.wrapper = wrapper;
    }
}

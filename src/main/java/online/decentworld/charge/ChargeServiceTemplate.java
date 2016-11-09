package online.decentworld.charge;

import online.decentworld.charge.charger.*;
import online.decentworld.charge.interceptor.TransferInterceptor;
import online.decentworld.charge.receipt.ChargeReceipt;
import online.decentworld.charge.receipt.DefaultChargeReceiptWrapper;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.interceptor.ChargeInterceptor;
import online.decentworld.charge.interceptor.LogInterceptor;
import online.decentworld.charge.price.*;
import online.decentworld.charge.receipt.ChargeReceiptWrapper;
import online.decentworld.rdb.mapper.ConsumePriceMapper;
import online.decentworld.rdb.mapper.OrderMapper;
import online.decentworld.rdb.mapper.TransferHistoryMapper;
import online.decentworld.rdb.mapper.WealthMapper;

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
        headInterceptor.afterCharge(event,chargeResult);
        return wrapper.wrapChargeResult(chargeResult,price);
    }

    @Override
    public ChargeInterceptor addInterceptor(ChargeInterceptor interceptor) {
        if(headInterceptor==null){
            this.headInterceptor=interceptor;
        }else{
            headInterceptor.addToTail(interceptor);
        }
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


    public static ChargeServiceTemplate defaultService(WealthMapper wealthMapper,ConsumePriceMapper consumePriceMapper,OrderMapper orderMapper,TransferHistoryMapper transferHistoryMapper){
        ChargeServiceTemplate service=new ChargeServiceTemplate();
        TransferInterceptor interceptor=new TransferInterceptor(transferHistoryMapper);

        service.setChargeReceiptWrapper(new DefaultChargeReceiptWrapper());
        service.setChargerFactory(new DefaultChargerFactory(new DefalutCharger(new DBCharger(wealthMapper))));
        service.addInterceptor(new LogInterceptor()).addToTail(interceptor);
        DBPriceCounter dbPriceCounter=new DBPriceCounter();
        dbPriceCounter.setPriceMapper(consumePriceMapper);
        MessagePriceCounter messagePriceCounter=new MessagePriceCounter();
        ReChargePriceCounter reChargePriceCounter =new ReChargePriceCounter();
        reChargePriceCounter.setOrderMapper(orderMapper);
        ConfigPriceCounterFactory configPriceCounterFactory=new ConfigPriceCounterFactory(dbPriceCounter,messagePriceCounter, reChargePriceCounter);
        service.setPriceCounterFactory(configPriceCounterFactory);
        return service;
    }
}

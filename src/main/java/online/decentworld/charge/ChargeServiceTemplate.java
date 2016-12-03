package online.decentworld.charge;

import online.decentworld.charge.charger.*;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.interceptor.ChargeInterceptor;
import online.decentworld.charge.interceptor.LogInterceptor;
import online.decentworld.charge.interceptor.TipInterceptor;
import online.decentworld.charge.interceptor.TransferInterceptor;
import online.decentworld.charge.price.*;
import online.decentworld.charge.receipt.ChargeReceipt;
import online.decentworld.charge.receipt.ChargeReceiptWrapper;
import online.decentworld.charge.receipt.DefaultChargeReceiptWrapper;
import online.decentworld.rdb.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* Created by Sammax on 2016/9/22.
*/
//@Component
public class ChargeServiceTemplate implements ChargeService {


    private  static Logger logger= LoggerFactory.getLogger(ChargeServiceTemplate.class);


//    @Autowired
    private ChargerFactory chargerFactory;
//    @Autowired
    private PriceCounterFactory priceCounterFactory;
//    @Autowired
    private ChargeReceiptWrapper wrapper;
//    @Resource(name = "tipInterceptor")
    private ChargeInterceptor tipInterceptor;
//    @Resource(name = "transferInterceptor")
    private ChargeInterceptor transferInterceptor;
//    @Resource(name = "worthEventInterceptor")
    private ChargeInterceptor worthEventInterceptor;


    private ChargeInterceptor headInterceptor;

//    @PostConstruct
    public void init(){
        //init interceptor chain
        headInterceptor=new LogInterceptor();
        headInterceptor.addToTail(tipInterceptor).addToTail(transferInterceptor)
        .addToTail(worthEventInterceptor);
    }

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



    public static ChargeServiceTemplate defaultService(WealthMapper wealthMapper,ConsumePriceMapper consumePriceMapper,OrderMapper orderMapper,TransferHistoryMapper transferHistoryMapper,TipRecordsMapper tipRecordsMapper){
        ChargeServiceTemplate service=new ChargeServiceTemplate();
        TransferInterceptor interceptor=new TransferInterceptor(transferHistoryMapper);
        TipInterceptor tipInterceptor=new TipInterceptor(tipRecordsMapper);
        service.setChargeReceiptWrapper(new DefaultChargeReceiptWrapper());
        service.setChargerFactory(new DefaultChargerFactory(new DefalutCharger(new DBCharger(wealthMapper))));
        service.addInterceptor(new LogInterceptor()).addToTail(interceptor).addToTail(tipInterceptor);
        DBPriceCounter dbPriceCounter=new DBPriceCounter();
        dbPriceCounter.setPriceMapper(consumePriceMapper);
        MessagePriceCounter messagePriceCounter=new MessagePriceCounter();
        RechargePriceCounter rechargePriceCounter =new RechargePriceCounter();
        rechargePriceCounter.setOrderMapper(orderMapper);
        ConfigPriceCounterFactory configPriceCounterFactory=new ConfigPriceCounterFactory(dbPriceCounter,messagePriceCounter, rechargePriceCounter);
        service.setPriceCounterFactory(configPriceCounterFactory);
        return service;
    }

    public static ChargeServiceTemplate defaultService(WealthMapper wealthMapper,ConsumePriceMapper consumePriceMapper,OrderMapper orderMapper){
        ChargeServiceTemplate service=new ChargeServiceTemplate();
//        TransferInterceptor interceptor=new TransferInterceptor(transferHistoryMapper);
//        TipInterceptor tipInterceptor=new TipInterceptor(tipRecordsMapper);
        service.setChargeReceiptWrapper(new DefaultChargeReceiptWrapper());
        service.setChargerFactory(new DefaultChargerFactory(new DefalutCharger(new DBCharger(wealthMapper))));
        service.addInterceptor(new LogInterceptor());
        DBPriceCounter dbPriceCounter=new DBPriceCounter();
        dbPriceCounter.setPriceMapper(consumePriceMapper);
        MessagePriceCounter messagePriceCounter=new MessagePriceCounter();
        RechargePriceCounter rechargePriceCounter =new RechargePriceCounter();
        rechargePriceCounter.setOrderMapper(orderMapper);
        ConfigPriceCounterFactory configPriceCounterFactory=new ConfigPriceCounterFactory(dbPriceCounter,messagePriceCounter, rechargePriceCounter);
        service.setPriceCounterFactory(configPriceCounterFactory);
        return service;
    }
}

package online.decentworld.charge.price;

import online.decentworld.charge.PriceCounterFactory;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.UnsupportChargeEvent;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.charge.type.StableConsumeType;

/**
 * Created by Sammax on 2016/10/6.
 */
public class ConfigPriceCounterFactory implements PriceCounterFactory {

    private DBPriceCounter priceCounter;

    private MessagePriceCounter messagePriceCounter;

    private RechargePriceCounter rechargePriceCounter;

    private PlainMessagePriceCounter plainMessagePriceCounter=new PlainMessagePriceCounter();

    public ConfigPriceCounterFactory(DBPriceCounter priceCounter, MessagePriceCounter messagePriceCounter, RechargePriceCounter rechargePriceCounter) {
        this.priceCounter = priceCounter;
        this.messagePriceCounter = messagePriceCounter;
        this.rechargePriceCounter = rechargePriceCounter;
    }

    @Override
    public PriceCounter getPriceCounter(ChargeEvent event) throws UnsupportChargeEvent {
        ConsumeType type=event.getConsumeType();
        if(type instanceof StableConsumeType){
            return priceCounter;
        }else if(type== MutableConsumeType.RECHARGE){
            return rechargePriceCounter;
        }else if(type== MutableConsumeType.CHAT){
            return messagePriceCounter;
        }else if(type==MutableConsumeType.PLAINCHAT){
            return plainMessagePriceCounter;
        }
        throw new UnsupportChargeEvent();
    }
}

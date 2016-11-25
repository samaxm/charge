package online.decentworld.charge.price;

import online.decentworld.charge.PriceCounterFactory;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.UnsupportChargeEvent;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.charge.type.StableConsumeType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Sammax on 2016/10/6.
 */
@Component
public class ConfigPriceCounterFactory implements PriceCounterFactory {


    @Resource(name = "dBPriceCounter")
    private DBPriceCounter priceCounter;
    @Resource(name = "messagePriceCounter")
    private MessagePriceCounter messagePriceCounter;
    @Resource(name = "rechargePriceCounter")
    private RechargePriceCounter rechargePriceCounter;
    @Resource(name = "plainMessagePriceCounter")
    private PlainMessagePriceCounter plainMessagePriceCounter;
    @Resource(name = "transferPriceCounter")
    private TransferPriceCounter transferPriceCounter;
    @Resource(name = "tipPriceCounter")
    private TipPriceCounter tipPriceCounter;
    @Resource(name = "worthChangePriceCounter")
    private WorthChangePriceCounter worthChangePriceCounter;

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
        }else if(type==MutableConsumeType.TRANSFER){
            return transferPriceCounter;
        }else if(type==MutableConsumeType.TIP){
            return tipPriceCounter;
        }else if(type==MutableConsumeType.CHANGE_WORTH){
            return worthChangePriceCounter;
        }
        throw new UnsupportChargeEvent();
    }
}

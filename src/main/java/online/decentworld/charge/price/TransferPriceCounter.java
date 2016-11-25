package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.TransferEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/11/8.
 */

@Component(value = "transferPriceCounter")
public class TransferPriceCounter implements PriceCounter {
    @Override
    public PriceCountResult getPrice(ChargeEvent event) throws IllegalChargeException {
        if(event instanceof TransferEvent){
            return new PriceCountResult(((TransferEvent) event).getAmount());
        }else{
            throw new IllegalChargeException();
        }
    }
}

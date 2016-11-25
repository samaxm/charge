package online.decentworld.charge.price;

import online.decentworld.charge.event.ChangeWorthEvent;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/11/25.
 */
@Component("worthChangePriceCounter")
public class WorthChangePriceCounter implements PriceCounter  {
    @Override
    public PriceCountResult getPrice(ChargeEvent event) throws IllegalChargeException {
        ChangeWorthEvent changeWorthEvent= (ChangeWorthEvent) event;
        return new PriceCountResult(changeWorthEvent.getWorth());
    }
}

package online.decentworld.charge;

import online.decentworld.charge.charger.ICharger;
import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/24.
 */
public interface ChargerFactory {
    public ICharger getCharger(ChargeEvent event);
}

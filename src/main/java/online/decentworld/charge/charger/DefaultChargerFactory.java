package online.decentworld.charge.charger;

import online.decentworld.charge.ChargerFactory;
import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/10/6.
 */
public class DefaultChargerFactory implements ChargerFactory {

    private DefalutCharger charger=new DefalutCharger();
    @Override
    public ICharger getCharger(ChargeEvent event) {
        return charger;
    }
}

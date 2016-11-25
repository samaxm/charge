package online.decentworld.charge.charger;

import online.decentworld.charge.ChargerFactory;
import online.decentworld.charge.event.ChargeEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/6.
 */
@Component
public class DefaultChargerFactory implements ChargerFactory {

    private DefalutCharger charger;

    public DefaultChargerFactory(DefalutCharger charger) {
        this.charger = charger;
    }

    @Override
    public ICharger getCharger(ChargeEvent event) {
        return charger;
    }
}

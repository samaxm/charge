package online.decentworld.charge.charger;

import online.decentworld.charge.price.PriceCountResult;

/**
 * Created by Sammax on 2016/9/8.
 */
public interface ICharger {

    public ChargeResult charge(PriceCountResult price);
}

package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.SingleChargeEvent;
import online.decentworld.charge.exception.UnsupportChargeEvent;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.rdb.entity.ConsumePrice;
import online.decentworld.rdb.mapper.ConsumePriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/12.
 */
@Component(value = "dBPriceCounter")
public class DBPriceCounter implements PriceCounter{

    @Autowired
    private ConsumePriceMapper priceMapper;

    private static Logger logger= LoggerFactory.getLogger(DBPriceCounter.class);

    @Override
    public PriceCountResult getPrice(ChargeEvent event) throws UnsupportChargeEvent {
        if(event.getConsumeType() instanceof MutableConsumeType){
            throw new UnsupportChargeEvent();
        }
        String typeName=event.getConsumeType().getTypeIdentify();
        ConsumePrice price=priceMapper.selectByPrimaryKey(typeName);
        if(event instanceof SingleChargeEvent){
            return new PriceCountResult(price.getPrice());
        }else{
            return new P2PPriceCountResult(price.getPrice(),price.getPrice());
        }
    }

    public void setPriceMapper(ConsumePriceMapper priceMapper) {
        this.priceMapper = priceMapper;
    }
}

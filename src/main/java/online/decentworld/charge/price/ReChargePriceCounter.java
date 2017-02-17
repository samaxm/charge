package online.decentworld.charge.price;

import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.RechargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.exception.UnsupportChargeEvent;
import online.decentworld.rdb.entity.Order;
import online.decentworld.rdb.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/24.
 */
@Component
public class RechargePriceCounter implements PriceCounter
{
    @Autowired
    private OrderMapper orderMapper;

    private static Logger logger= LoggerFactory.getLogger(RechargePriceCounter.class);

    @Override
    public PriceCountResult getPrice(ChargeEvent event) throws IllegalChargeException {
        if(event instanceof RechargeEvent){
            RechargeEvent re=(RechargeEvent)event;
            Order order=orderMapper.selectByPrimaryKey(re.getOrderNum());
                        if(order==null){
                logger.warn("[RECHARGE_ERROR_ORDER_NOT_FOUND] orderNum#"+re.getOrderNum());
                throw new IllegalChargeException();
            }else{
                if(!order.getDwid().equals(re.getDwID())||order.getAmount()!=re.getAmount()){
                    logger.warn("[RECHARGE_ERROR_ORDER_AMOUNT_NOT_MATCH] orderNum#"+re.getOrderNum()+" dwID#"+re.getDwID()+" amount#"+re.getAmount());
                    throw new IllegalChargeException();
                }else {
                    if(order.getIsPaid()){
                        return new PriceCountResult(0);
                    }
                    orderMapper.updateStatus(re.getOrderNum(),true);
                    re.setOrder(order);
                    return new PriceCountResult(order.getAmount());
                }
            }
        }else{
            throw new UnsupportChargeEvent();
        }
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
}

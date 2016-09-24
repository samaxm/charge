package online.decentworld.charge.service;

import online.decentworld.rdb.entity.Order;

/**
 * Created by Sammax on 2016/9/24.
 */
public interface IOrderService {
    Order createOrder(PayChannel channel,int amount,String dwID,OrderType orderTpye,String extra,String ip,String msg) throws Exception;
    Order getOrder(String orderNum);
    boolean checkOrder(Order order);
}

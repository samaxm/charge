package online.decentworld.charge.service;

import online.decentworld.rdb.entity.Order;

/**
 * Created by Sammax on 2016/9/24.
 */
public interface IOrderService {
    /***
     *
     * @param channel
     * @param amount
     * @param dwID
     * @param orderTpye
     * @param extra
     * @param ip
     * @return
     * @throws Exception
     */
    OrderReceipt createOrder(PayChannel channel,int amount,String dwID,OrderType orderTpye,String extra,String ip,String msg) throws Exception;

    /**
     *
     * @param orderNum
     * @return
     */
    Order getOrder(String orderNum);

    /**
     *
     * @param order
     * @return
     */
    boolean checkOrder(Order order);

    void receiverOrderSuccessResponse(String orderNum);
}

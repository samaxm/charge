package online.decentworld.charge.service.spi;

import online.decentworld.charge.service.*;
import online.decentworld.rdb.entity.Order;
import online.decentworld.rdb.mapper.OrderMapper;

import java.util.Date;

/**
 * Created by Sammax on 2016/9/24.
 */
public class OrderService implements IOrderService
{

    private OrderMapper orderMapper;

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderReceipt createOrder(PayChannel channel, int amount, String dwID, OrderType orderTpye, String extra, String ip,String msg) throws Exception {
        Order order=new Order();
        order.setAmount(amount);
        order.setChannel(channel.getChannelString());
        order.setDwid(dwID);
        order.setType(orderTpye.getValue());
        order.setIsPaid(false);
        order.setTime(new Date());
        order.setOrdernumer(dwID + "" + System.currentTimeMillis());
        order.setExtra(extra);
        Object request=RequestCreatorFactory.getRequestCreator(channel).getRequestData(order,ip,msg);
        orderMapper.insertSelective(order);
        return new OrderReceipt(order.getOrdernumer(),request);
    }

    @Override
    public Order getOrder(String orderNum) {
        return orderMapper.selectByPrimaryKey(orderNum);
    }

    @Override
    public boolean checkOrder(Order order) {
        return false;
    }

    @Override
    public void receiverOrderSuccessResponse(String orderNum) {
        orderMapper.updateStatus(orderNum,true);
    }
}

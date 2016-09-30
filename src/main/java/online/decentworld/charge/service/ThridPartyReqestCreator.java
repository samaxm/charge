package online.decentworld.charge.service;

import online.decentworld.rdb.entity.Order;

/**
 * Created by Sammax on 2016/9/26.
 */
public interface ThridPartyReqestCreator {
    Object getRequestData(Order order,String ip,String msg) throws Exception;
}

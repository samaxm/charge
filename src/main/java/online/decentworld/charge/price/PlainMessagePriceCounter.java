package online.decentworld.charge.price;

import online.decentworld.cache.redis.RedisTemplate;
import online.decentworld.cache.redis.ReturnResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import redis.clients.jedis.Jedis;

/**
 * Created by Sammax on 2016/9/23.
 */
public class PlainMessagePriceCounter extends RedisTemplate implements PriceCounter {

    private static String CHAT_COUNT="CHAT:COUNT";

    @Override
    public PriceCountResult getPrice(ChargeEvent event) {

        if(event instanceof P2PChargeEvent){
            P2PChargeEvent p2PChargeEvent=(P2PChargeEvent)event;
            final String fromID=p2PChargeEvent.getPayer();
            final String toID=p2PChargeEvent.getPayee();
            ReturnResult result=cache((Jedis jedis) -> {
                //发送方的聊天状态计数器+1
                Long from_num = jedis.hincrBy(CHAT_COUNT, fromID + toID, 1);
                //接收方的聊天状态计数器重置为0
                jedis.hset(CHAT_COUNT, toID + fromID, "0");
                if (from_num > 3) {
                    //发送方连续对话三句且接收方并未回复
                    return ReturnResult.result(new P2PPriceCountResult(-1, 0));
                } else {
                    return ReturnResult.result(new P2PPriceCountResult(-1, 1));
                }
            });
            if(result.isSuccess()){
                return (PriceCountResult) result.getResult();
            }else{
                return new P2PPriceCountResult(-1, 1);
            }
        }
        return null;
    }



}

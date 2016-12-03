package online.decentworld.charge.price;

import online.decentworld.cache.redis.CacheKey;
import online.decentworld.cache.redis.RedisTemplate;
import online.decentworld.cache.redis.ReturnResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import online.decentworld.rdb.entity.User;
import online.decentworld.rdb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/23.
 */
@Component(value = "plainMessagePriceCounter")
public class PlainMessagePriceCounter extends RedisTemplate implements PriceCounter {

    private static String CHAT_COUNT="CHAT:COUNT";
    @Autowired
    private UserMapper userMapper;


    @Override
    public PriceCountResult getPrice(ChargeEvent event) {

        if(event instanceof P2PChargeEvent){
            P2PChargeEvent p2PChargeEvent=(P2PChargeEvent)event;
            final String fromID=p2PChargeEvent.getPayer();
            final String toID=p2PChargeEvent.getPayee();
            ReturnResult result=cache(jedis -> {
                String fromWorthString=jedis.hget(CacheKey.WORTH, fromID);
                String toWorthString=jedis.hget(CacheKey.WORTH, toID);
                Integer fromWorth;
                Integer toWorth;
                if(fromWorthString==null){
                    User user=userMapper.selectByPrimaryKey(fromID);
                    fromWorth=user.getWorth();
                    jedis.hset(CacheKey.WORTH, fromID, String.valueOf(fromWorth));
                }else {
                    fromWorth=Integer.parseInt(fromWorthString);
                }

                if(toWorthString==null){
                    User user=userMapper.selectByPrimaryKey(toID);
                    toWorth=user.getWorth();
                    jedis.hset(CacheKey.WORTH, fromID,String.valueOf(toWorth));
                }else {toWorth=Integer.parseInt(toWorthString);}
                //发送方的聊天状态计数器+1
                Long from_num = jedis.hincrBy(CHAT_COUNT, fromID + toID, 1);
                //接收方的聊天状态计数器重置为0
                jedis.hset(CHAT_COUNT, toID + fromID, "0");
                if (from_num > 3) {
                    //发送方连续对话三句且接收方并未回复
                    return ReturnResult.result(new P2PPriceCountResult(toWorth, 0));
                } else {
                    return ReturnResult.result(new P2PPriceCountResult(toWorth,fromWorth));
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

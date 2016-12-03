package online.decentworld.charge.interceptor;

import online.decentworld.cache.redis.SessionCache;
import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChangeWorthEvent;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.rdb.entity.User;
import online.decentworld.rdb.entity.WorthChangeRecords;
import online.decentworld.rdb.mapper.UserMapper;
import online.decentworld.rdb.mapper.WorthChangeRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Sammax on 2016/11/25.
 */
@Component("worthEventInterceptor")
public class WorthEventInterceptor extends AbstractChargeInterceptor
{

    @Autowired
    private WorthChangeRecordsMapper worthChangeRecordsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SessionCache sessionCache;

    @Override
    protected void doBeforeCharge(ChargeEvent event) throws IllegalChargeException {
        ChangeWorthEvent changeWorthEvent=(ChangeWorthEvent)event;
        if(changeWorthEvent.getWorth()<1){
            throw new IllegalChargeException("worth can't smaller than 1");
        }
    }

    @Override
    @Transactional
    protected void doAfterCharge(ChargeEvent event, ChargeResult result) throws IllegalChargeException {
        if(result.getStatusCode()== ChargeResultCode.SUCCESS) {
            //charge success and record to worth change history
            ChangeWorthEvent changeWorthEvent = (ChangeWorthEvent) event;
            WorthChangeRecords records = new WorthChangeRecords();
            User user = userMapper.selectByPrimaryKey(changeWorthEvent.getDwID());
            records.setDwid(changeWorthEvent.getDwID());
            records.setTime(new Date());
            records.setWorthBefore(user.getWorth());
            records.setWorthAfter(changeWorthEvent.getWorth());
            //add change record
            worthChangeRecordsMapper.insert(records);
            //change worth
            User change = new User();
            change.setId(user.getId());
            change.setWorth(changeWorthEvent.getWorth());
            userMapper.updateByPrimaryKeySelective(change);
            //change worth cache
            if(!sessionCache.setUserWorth(user.getId(),String.valueOf(changeWorthEvent.getWorth()))){
                throw new RuntimeException("change worth cache fail");
            }
        }
    }

    @Override
    public boolean accept(ConsumeType type) {
        if(type== MutableConsumeType.CHANGE_WORTH){
            return true;
        }else{
            return false;
        }
    }
}

package online.decentworld.charge.interceptor;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.TipEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.rdb.entity.TipRecords;
import online.decentworld.rdb.mapper.TipRecordsMapper;
import online.decentworld.tools.LoggerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/11/16.
 */
public class TipInterceptor extends AbstractChargeInterceptor {

    private static Logger logger= LoggerFactory.getLogger(LoggerIdentifier.DB_ERROR);

    private TipRecordsMapper tipRecordsMapper;

    public TipInterceptor(TipRecordsMapper tipRecordsMapper) {
        this.tipRecordsMapper = tipRecordsMapper;
    }

    @Override
    protected void doBeforeCharge(ChargeEvent event) throws IllegalChargeException {
        //do nothing
    }

    @Override
    protected void doAfterCharge(ChargeEvent event, ChargeResult result) throws IllegalChargeException {
        if(result.getStatusCode()== ChargeResultCode.SUCCESS){
            //save tip records
            try {
                TipEvent tipEvent = (TipEvent) event;
                TipRecords record = new TipRecords(tipEvent.getPayer(), tipEvent.getPayee(), tipEvent.getAmount());
                tipRecordsMapper.insert(record);
            }catch (Exception e){
                logger.warn("[SAVING_TIPRECORDS]",e);
            }
        }
    }

    @Override
    public boolean accept(ConsumeType type) {
        if(type==MutableConsumeType.TIP){
            return true;
        }
        return false;
    }

    public void setTipRecordsMapper(TipRecordsMapper tipRecordsMapper) {
        this.tipRecordsMapper = tipRecordsMapper;
    }
}

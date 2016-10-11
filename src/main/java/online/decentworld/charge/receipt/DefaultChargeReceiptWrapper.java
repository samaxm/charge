package online.decentworld.charge.receipt;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.price.PriceCountResult;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.rpc.dto.message.types.ChatRelation;
import online.decentworld.rpc.dto.message.types.ChatStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/10/7.
 */
public class DefaultChargeReceiptWrapper implements ChargeReceiptWrapper {

    private static Logger logger= LoggerFactory.getLogger(ChargeReceiptWrapper.class);

    @Override
    public ChargeReceipt wrapChargeResult(ChargeResult result, PriceCountResult price) {
        ConsumeType type=result.getType();
        logger.debug("[CONSUME_TYPE]#"+type);
        if(type== MutableConsumeType.RECHARGE){
            return new PlainChargeReceipt(result);
        }else  if(type==MutableConsumeType.PLAINCHAT){
            return new MessageReceipt(result, ChatRelation.STRANGER, ChatStatus.NORMAL);
        }else{
            return null;
        }
    }
}

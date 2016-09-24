package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.rdb.entity.DBChargeResult;
import online.decentworld.rdb.mapper.WealthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* Created by Sammax on 2016/9/8.
*/
public class DBCharger {

    private static Logger logger= LoggerFactory.getLogger(DBCharger.class);

    private WealthMapper wealthMapper;


    public P2PChargeResult p2pCharge(String payerID, String payeeID, int payerChargeAmount, int payeeChargeAmount) {
        DBChargeResult payerResult=null;
        DBChargeResult payeeResult = null;
        P2PChargeResult result =new P2PChargeResult();
        if(payerChargeAmount!=0) {
            try {
                payerResult = wealthMapper.charge(payerID, payerChargeAmount);
                result.setPayerWealth(payerResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                return  result;
            }
        }else{
            //flag -1 means no change
            result.setPayerWealth(-1);
        }
        if(payeeChargeAmount!=0) {
            try {
                payeeResult = wealthMapper.charge(payeeID, payeeChargeAmount);
                result.setPayeeWealth(payeeResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                //TODO:payback
                return  result;
            }
        }else {
            //flag -1 means no change
            result.setPayeeWealth(-1);
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }

    public ChargeResult charge(String dwID, int chargeAmount) {
        DBChargeResult dbResult = null;
        ChargeResult result =new ChargeResult();
        if(chargeAmount!=0) {
            try {
                dbResult = wealthMapper.charge(dwID, chargeAmount);
                result.setPayerWealth(dbResult.getNewWealth());
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("", ex);
                return  result;
            }
        }else{
            //flag -1 means no change
            result.setPayerWealth(-1);
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }



}

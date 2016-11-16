package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.rdb.entity.DBChargeResult;
import online.decentworld.rdb.entity.Wealth;
import online.decentworld.rdb.mapper.WealthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* Created by Sammax on 2016/9/8.
*/
public class DBCharger {

    private static Logger logger= LoggerFactory.getLogger(DBCharger.class);

    private WealthMapper wealthMapper;

    public DBCharger(WealthMapper wealthMapper) {
        this.wealthMapper = wealthMapper;
    }

    public P2PChargeResult p2pCharge(String payerID, String payeeID, int payerChargeAmount, int payeeChargeAmount) {
        DBChargeResult payerResult;
        DBChargeResult payeeResult;
        P2PChargeResult result =new P2PChargeResult();
        if(payerChargeAmount!=0) {
            try {
                payerResult = wealthMapper.charge(payerID, payerChargeAmount);

                logger.debug("[PAYER_CHARGE_RESULT] code#"+payerResult.getResultCode()+" wealth#"+payerResult.getNewWealth());

                if(payerResult.getResultCode()==DBChargeResult.WEALTH_NOT_ENOUGH){
                    result.setStatusCode(ChargeResultCode.WEALTH_LACK);
                    return result;
                }else if(payerResult.getResultCode()!=DBChargeResult.SUCCESS){
                    result.setStatusCode(ChargeResultCode.FAIL);
                    return result;
                }
                result.setPayerWealth(payerResult.getNewWealth());
                result.setPayerID(payerID);
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("[PAYER_CHARGE_FAIL]", ex);
                return  result;
            }
        }else{
            Wealth w=wealthMapper.selectByPrimaryKey(payerID);
            result.setPayerWealth(w.getWealth());
        }
        if(payeeChargeAmount!=0) {
            try {
                payeeResult = wealthMapper.charge(payeeID, payeeChargeAmount);

                logger.debug("[PAYEE_CHARGE_RESULT] code#"+payeeResult.getResultCode()+" wealth#"+payeeResult.getNewWealth());

                if(payeeResult.getResultCode()==DBChargeResult.WEALTH_NOT_ENOUGH){
                    result.setStatusCode(ChargeResultCode.WEALTH_LACK);
                    return result;
                }else if(payeeResult.getResultCode()!=DBChargeResult.SUCCESS){
                    result.setStatusCode(ChargeResultCode.FAIL);
                    return result;
                }
                result.setPayeeWealth(payeeResult.getNewWealth());
                result.setPayeeID(payeeID);
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("[PAYEE_CHARGE_FAIL] thread#"+Thread.currentThread().getId(), ex);
                try {
                    //SIMPLE PAY BACK PAYER
                    wealthMapper.charge(payerID, -payerChargeAmount);
                }catch (Exception e){
                    logger.warn("[PAYEE_PAYBACK_FAIL] thread#"+Thread.currentThread().getId(), e);
                }
                return  result;
            }
        }else {
            Wealth w=wealthMapper.selectByPrimaryKey(payeeID);
            result.setPayeeWealth(w.getWealth());
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }

    public ChargeResult charge(String dwID, int chargeAmount) {
        DBChargeResult dbResult;
        ChargeResult result =new ChargeResult();
        if(chargeAmount!=0) {
            try {
                dbResult = wealthMapper.charge(dwID, chargeAmount);
                if(dbResult.getResultCode()==DBChargeResult.WEALTH_NOT_ENOUGH){
                    result.setStatusCode(ChargeResultCode.WEALTH_LACK);
                    return result;
                }else if(dbResult.getResultCode()!=DBChargeResult.SUCCESS){
                    result.setStatusCode(ChargeResultCode.FAIL);
                    return result;
                }
                result.setPayerWealth(dbResult.getNewWealth());
                result.setPayerID(dwID);
            } catch (Exception ex) {
                result.setStatusCode(ChargeResultCode.FAIL);
                logger.warn("[PAYER_CHARGE_FAIL]", ex);
                return  result;
            }
        }else{
            Wealth w=wealthMapper.selectByPrimaryKey(dwID);
            result.setPayerWealth(w.getWealth());
        }
        result.setStatusCode(ChargeResultCode.SUCCESS);
        return result;
    }



}

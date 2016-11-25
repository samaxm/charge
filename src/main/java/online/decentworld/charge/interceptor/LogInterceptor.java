package online.decentworld.charge.interceptor;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.charger.P2PChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.P2PChargeEvent;
import online.decentworld.charge.event.SingleChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.type.ConsumeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/6.
 */
@Component("logInterceptor")
public class LogInterceptor extends AbstractChargeInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    protected void doBeforeCharge(ChargeEvent event) throws IllegalChargeException {
        if (event instanceof SingleChargeEvent) {
            SingleChargeEvent se = (SingleChargeEvent) event;
            logger.info("[CHARGE＿BEFORE] type#" + event.getConsumeType()
                    + " dwID#" + se.getDwID() + " operation#" + se.getOperation()
                    + " thread#" + Thread.currentThread().getId());
        } else {
            P2PChargeEvent pe = (P2PChargeEvent) event;
            logger.info("[CHARGE＿BEFORE] type#" + event.getConsumeType()
                    + " payer#" + pe.getPayer() + " payer_operation#" + pe.getPayerChargeOperation()
                    + " payee#" + pe.getPayee() + " payee_operation#" + pe.getPayeeChargeOperation()
                    + " thread#" + Thread.currentThread().getId());
        }

    }

    @Override
    public boolean accept(ConsumeType type) {
        return true;
    }


    @Override
    protected void doAfterCharge(ChargeEvent event,ChargeResult result) throws IllegalChargeException {
        if (result instanceof P2PChargeResult) {
            P2PChargeEvent pe = (P2PChargeEvent) event;
            P2PChargeResult pr = (P2PChargeResult) result;
            logger.info("[CHARGE＿AFTER] type#" + pr.getType()
                    + " payer#" + pe.getPayer() + " payer_operation#" + pe.getPayerChargeOperation()
                    + " payee#" + pe.getPayee() + " payee_operation#" + pe.getPayeeChargeOperation()
                    + " status#" + pr.getStatusCode() + " payerWealth#" + pr.getPayerWealth() + " payeeWealth#" + pr.getPayeeWealth()
                    + " thread#" + Thread.currentThread().getId());
        } else {
            SingleChargeEvent se = (SingleChargeEvent) event;
            logger.info("[CHARGE＿AFTER] type#" + result.getType()
                    + " dwID#" + se.getDwID() + " operation#" + se.getOperation()
                    + " status#" + result.getStatusCode() + " payerWealth#" + result.getPayerWealth()
                    + " thread#" + Thread.currentThread().getId());
        }

    }
}
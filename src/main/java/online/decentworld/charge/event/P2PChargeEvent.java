package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.type.ConsumeType;

/**
 * Created by Sammax on 2016/9/9.
 */
public class P2PChargeEvent implements ChargeEvent {

    private String payer;
    private String payee;
    private ChargeOperation payerChargeOperation;
    private ChargeOperation payeeChargeOperation;
    private ConsumeType type;

    public ChargeOperation getPayerChargeOperation() {
        return payerChargeOperation;
    }

    public void setPayerChargeOperation(ChargeOperation payerChargeOperation) {
        this.payerChargeOperation = payerChargeOperation;
    }

    public ChargeOperation getPayeeChargeOperation() {
        return payeeChargeOperation;
    }

    public void setPayeeChargeOperation(ChargeOperation payeeChargeOperation) {
        this.payeeChargeOperation = payeeChargeOperation;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }


    public P2PChargeEvent(String payer, String payee) {
        this.payer = payer;
        this.payee = payee;
    }

    public P2PChargeEvent(String payer, String payee, ChargeOperation payerChargeOperation, ChargeOperation payeeChargeOperation, ConsumeType type) {
        this.payer = payer;
        this.payee = payee;
        this.payerChargeOperation = payerChargeOperation;
        this.payeeChargeOperation = payeeChargeOperation;
        this.type = type;
    }

    @Override
    public ConsumeType getConsumeType() {
        return type;
    }
}

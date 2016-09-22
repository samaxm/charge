package online.decentworld.charge.price;

import online.decentworld.charge.ConsumType;

/**
 * Created by Sammax on 2016/9/9.
 */
public class P2PPriceCountResult  {


    private ConsumType type;
    private int payerChargeAmount;
    private int payeeChargeAmount;
    private String payer;
    private String payee;

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

    public int getPayeeChargeAmount() {
        return payeeChargeAmount;
    }

    public void setPayeeChargeAmount(int payeeChargeAmount) {
        this.payeeChargeAmount = payeeChargeAmount;
    }

    public int getPayerChargeAmount() {
        return payerChargeAmount;
    }

    public void setPayerChargeAmount(int payerChargeAmount) {
        this.payerChargeAmount = payerChargeAmount;
    }

    public ConsumType getType() {
        return null;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }

    public P2PPriceCountResult(ConsumType type, int payerChargeAmount, int payeeChargeAmount) {
        this.type = type;
        this.payerChargeAmount = payerChargeAmount;
        this.payeeChargeAmount = payeeChargeAmount;
    }
}

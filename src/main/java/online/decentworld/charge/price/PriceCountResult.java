package online.decentworld.charge.price;

/**
 * Created by Sammax on 2016/9/9.
 */
public class PriceCountResult {

    private int payerChargeAmount;

    public PriceCountResult(int payerChargeAmount) {
        this.payerChargeAmount = payerChargeAmount;
    }

    public int getPayerChargeAmount() {
        return payerChargeAmount;
    }

    public void setPayerChargeAmount(int payerChargeAmount) {
        this.payerChargeAmount = payerChargeAmount;
    }
}

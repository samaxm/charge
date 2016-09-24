package online.decentworld.charge.price;

/**
 * Created by Sammax on 2016/9/9.
 */
public class P2PPriceCountResult extends PriceCountResult {


    private int payeeChargeAmount;

    public int getPayeeChargeAmount() {
        return payeeChargeAmount;
    }

    public void setPayeeChargeAmount(int payeeChargeAmount) {
        this.payeeChargeAmount = payeeChargeAmount;
    }

    public P2PPriceCountResult(int payerChargeAmount, int payeeChargeAmount) {
        super(payerChargeAmount);
        this.payeeChargeAmount = payeeChargeAmount;
    }
}

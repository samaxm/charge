package online.decentworld.charge.charger;


/**
 * Created by Sammax on 2016/9/7.
 */
public class P2PChargeResult extends ChargeResult{

    private int payeeWealth;
    private String payeeID;
    public int getPayeeWealth() {
        return payeeWealth;
    }

    public void setPayeeWealth(int payeeWealth) {
        this.payeeWealth = payeeWealth;
    }

    public String getPayeeID() {
        return payeeID;
    }

    public void setPayeeID(String payeeID) {
        this.payeeID = payeeID;
    }
}

package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.type.ConsumeType;

/**
 * Created by Sammax on 2016/9/9.
 */
public class ChargeResult {
    private ChargeResultCode statusCode;
    private int payerWealth;
    private ConsumeType type;

    public ChargeResultCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ChargeResultCode statusCode) {
        this.statusCode = statusCode;
    }

    public int getPayerWealth() {
        return payerWealth;
    }

    public void setPayerWealth(int payerWealth) {
        this.payerWealth = payerWealth;
    }

    public ConsumeType getType() {
        return type;
    }

    public void setType(ConsumeType type) {
        this.type = type;
    }
}

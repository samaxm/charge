package online.decentworld.charge.charger;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.ConsumType;

/**
 * Created by Sammax on 2016/9/9.
 */
public class ChargeResult {
    private ChargeResultCode statusCode;
    private int payerWealth;
    private ConsumType type;

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

    public ConsumType getType() {
        return type;
    }

    public void setType(ConsumType type) {
        this.type = type;
    }
}

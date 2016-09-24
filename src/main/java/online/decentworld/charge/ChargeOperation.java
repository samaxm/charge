package online.decentworld.charge;

/**
 * Created by Sammax on 2016/9/24.
 */
public enum ChargeOperation {
    MINUS,PLUS;

    public int getChargePrice(int price){
        if(this==PLUS){
            price=Math.abs(price);
        }else{
            price=-Math.abs(price);
        }
        return price;
    }
}
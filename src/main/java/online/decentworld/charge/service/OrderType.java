package online.decentworld.charge.service;

/**
 * Created by Sammax on 2016/9/24.
 */
public enum OrderType {
    RECHARGE(0),VIP_REGISTER(1);
    private int value;
    private OrderType(int value){
        this.value=value;
    }
    public int getValue(){
        return this.value;
    }
    public static OrderType getOrderType(int value){
        for(OrderType type:OrderType.values()){
            if(type.value==value){
                return type;
            }
        }
        return null;
    }



}
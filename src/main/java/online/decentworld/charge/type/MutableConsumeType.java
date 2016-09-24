package online.decentworld.charge.type;

/**
 * Created by Sammax on 2016/9/23.
 */
public enum MutableConsumeType implements ConsumeType {

    PLAINCHAT("online.decentworld.charge.price.MessagePriceCounter"),
    CHAT("online.decentworld.charge.price.MessagePriceCounter"),
    RECHARGE("online.decentworld.charge.price.RechargePriceCounter");

    private String counterName;
    private MutableConsumeType(String counterName){
        this.counterName=counterName;
    }

    public String getCounterClassName(){
        return counterName;
    }

    @Override
    public String getTypeIdentify() {
        return name();
    }
}

package online.decentworld.charge.type;

/**
 * Created by Sammax on 2016/9/23.
 */
public enum  StableConsumeType implements ConsumeType {
    LIKE;
    @Override
    public String getTypeIdentify() {
        return name();
    }
}

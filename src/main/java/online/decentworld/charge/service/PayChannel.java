package online.decentworld.charge.service;

/**
 * Created by Sammax on 2016/9/24.
 */
public enum PayChannel {
    ALIPAY(0,"alipay"),WX(1,"wx"),APPLEPAY(2,"apple");
    private int channelCode;
    private String channelString;

    private PayChannel(int channelCode,String chanelString){
        this.channelCode=channelCode;
        this.channelString=chanelString;
    }

    public int getChannelCode(){
        return this.channelCode;
    }

    public String getChannelString(){
        return this.channelString;
    }

    public static PayChannel getChannel(int channelCode){
        for(PayChannel channel:PayChannel.values()){
            if(channel.getChannelCode()==channelCode){
                return channel;
            }
        }
        return null;
    }

    public static String getChannelStringByCode(int channelCode){
        PayChannel channel=getChannel(channelCode);
        if(channel!=null){
            return channel.channelString;
        }else{
            return null;
        }
    }


}

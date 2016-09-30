package online.decentworld.charge.service;

/**
 * Created by Sammax on 2016/9/24.
 */
public enum PayChannel {
    ALIPAY(0,"alipay","online.decentworld.charge.service.alipay.AlipayService"),WX(1,"wx","online.decentworld.charge.service.wx.WXPayService"),APPLEPAY(2,"apple","");
    private int channelCode;
    private String channelString;


    private String channelRequestCreator;

    private PayChannel(int channelCode,String chanelString,String channelRequestCreator){
        this.channelCode=channelCode;
        this.channelString=chanelString;
        this.channelRequestCreator=channelRequestCreator;
    }

    public String getChannelRequestCreator() {
        return channelRequestCreator;
    }

    public void setChannelRequestCreator(String channelRequestCreator) {
        this.channelRequestCreator = channelRequestCreator;
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

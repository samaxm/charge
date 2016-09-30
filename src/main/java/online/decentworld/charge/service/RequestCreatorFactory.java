package online.decentworld.charge.service;

/**
 * Created by Sammax on 2016/9/26.
 */
public class RequestCreatorFactory {

    public static ThridPartyReqestCreator getRequestCreator(PayChannel channel) throws Exception {
        ThridPartyReqestCreator creator=(ThridPartyReqestCreator)Class.forName(channel.getChannelRequestCreator()).newInstance();
        return  creator;
    }
}

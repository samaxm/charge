package online.decentworld.charge.receipt;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.charger.P2PChargeResult;
import online.decentworld.rpc.dto.message.types.ChatRelation;
import online.decentworld.rpc.dto.message.types.ChatStatus;

/**
 * Created by Sammax on 2016/10/11.
 */
public class MessageReceipt implements ChargeReceipt{

    private ChatRelation chatRelation;
    private ChatStatus chatStatus;
    private P2PChargeResult chargeResult;

    public MessageReceipt(ChargeResult result, ChatRelation chatRelation, ChatStatus chatStatus) {
        this.chargeResult=(P2PChargeResult)result;
        this.chatRelation = chatRelation;
        this.chatStatus = chatStatus;
    }

    public ChatRelation getChatRelation() {
        return chatRelation;
    }

    public ChatStatus getChatStatus() {
        return chatStatus;
    }

    public P2PChargeResult getChargeResult() {
        return chargeResult;
    }
}

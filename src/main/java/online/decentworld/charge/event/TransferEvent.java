package online.decentworld.charge.event;

import online.decentworld.charge.ChargeOperation;
import online.decentworld.charge.service.TransferAccountType;
import online.decentworld.charge.type.MutableConsumeType;

/**
 * Created by Sammax on 2016/11/7.
 */
public class TransferEvent extends SingleChargeEvent {


    private int amount;
    private String account;
    private Long recordID;
    private TransferAccountType accountType;
    private String ip;

    public TransferEvent(String dwID, int amount, String account,TransferAccountType accountType,String ip) {
        super(MutableConsumeType.TRANSFER, dwID, ChargeOperation.MINUS);
        this.amount = amount;
        this.account = account;
        this.accountType=accountType;
        this.ip=ip;
    }
    public TransferEvent(String dwID,long recordID){
        super(MutableConsumeType.TRANSFER, dwID, ChargeOperation.MINUS);
        this.recordID=recordID;
    }

    public void setRecordID(Long recordID) {
        this.recordID = recordID;
    }

    public Long getRecordID() {
        return recordID;
    }

    public int getAmount() {
        return amount;
    }

    public String getAccount() {
        return account;
    }

    public TransferAccountType getAccountType() {
        return accountType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

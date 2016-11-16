package online.decentworld.charge.interceptor;

import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.event.TransferEvent;
import online.decentworld.charge.exception.IllegalChargeException;
import online.decentworld.charge.service.TransferAccountType;
import online.decentworld.charge.service.TransferStatus;
import online.decentworld.charge.service.wx.WXTransferService;
import online.decentworld.charge.type.ConsumeType;
import online.decentworld.charge.type.MutableConsumeType;
import online.decentworld.rdb.entity.TransferHistory;
import online.decentworld.rdb.mapper.TransferHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/11/8.
 */
public class TransferInterceptor extends AbstractChargeInterceptor {


    private TransferHistoryMapper transferHistoryMapper;

    private WXTransferService wxTransferService=new WXTransferService();
    private static String notification="大腕提现";
    private static Logger logger= LoggerFactory.getLogger(TransferInterceptor.class);
    private static int MIN_TRANSFER_AMOUNT=100;

    public TransferInterceptor(TransferHistoryMapper transferHistoryMapper) {

        this.transferHistoryMapper = transferHistoryMapper;
    }

    @Override
    protected void doBeforeCharge(ChargeEvent event) throws IllegalChargeException {

        TransferEvent transferEvent=(TransferEvent)event;
        if(transferEvent.getRecordID()==null) {
            if(transferEvent.getAmount()<MIN_TRANSFER_AMOUNT){
                throw new IllegalChargeException();
            }
            TransferHistory transferHistory = new TransferHistory(transferEvent.getDwID(), transferEvent.getAmount(), TransferStatus.PROCESSING.name(), transferEvent.getAccountType().name(),transferEvent.getAccount(),transferEvent.getIp());
            transferHistoryMapper.insert(transferHistory);
            transferEvent.setRecordID(transferHistory.getId());
        }
    }

    @Override
    protected void doAfterCharge(ChargeEvent event,ChargeResult result) throws IllegalChargeException {
        TransferEvent transferEvent=(TransferEvent)event;
        if(result.getStatusCode()== ChargeResultCode.SUCCESS){
            if(transferEvent.getAccountType()== TransferAccountType.WXPAY){
                try {
                    //扣去10%手续费
                    wxTransferService.createTransfer(transferEvent.getAmount()*9/10,notification,transferEvent.getAccount(),String.valueOf(transferEvent.getRecordID()),transferEvent.getIp());
                    TransferHistory history=new TransferHistory();
                    history.setId(transferEvent.getRecordID());
                    history.setStatus(TransferStatus.SUCCESS.name());
                    transferHistoryMapper.updateByPrimaryKeySelective(history);
                } catch (Exception e) {
                    logger.warn("[FAILED_TRANSFER] dwID#"+transferEvent.getDwID()+" amount#"+transferEvent.getAmount()+" accountType#"+transferEvent.getAccountType()
                            +" recordID#"+transferEvent.getRecordID(),e);
                }
            }
        }else{
            TransferHistory history=new TransferHistory();
            history.setId(transferEvent.getRecordID());
            history.setStatus(TransferStatus.FAILED.name());
            transferHistoryMapper.updateByPrimaryKeySelective(history);
        }
    }

    @Override
    public boolean accept(ConsumeType type) {
       if(type == MutableConsumeType.TRANSFER){
           return true;
       }else{
           return false;
       }
    }

    public void setTransferHistoryMapper(TransferHistoryMapper transferHistoryMapper) {
        this.transferHistoryMapper = transferHistoryMapper;
    }
}

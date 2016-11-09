package online.decentworld.charge.interceptor;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;
import online.decentworld.charge.exception.IllegalChargeException;

/**
 * Created by Sammax on 2016/9/22.
 */
public abstract class AbstractChargeInterceptor implements  ChargeInterceptor {


    private ChargeInterceptor next;

    @Override
    public ChargeInterceptor addToTail(ChargeInterceptor next) {
        if(this.next==null){
            this.next=next;
        }else{
            this.next.addToTail(next);
        }
        return next;
    }

    @Override
    public void beforeCharge(ChargeEvent event) throws IllegalChargeException {
        if(accept(event.getConsumeType())){
            doBeforeCharge(event);
        }
        if(next!=null){
            next.beforeCharge(event);
        }
    }


    @Override
    public void afterCharge(ChargeEvent event,ChargeResult result) throws IllegalChargeException  {
        if(accept(result.getType())) {
            doAfterCharge(event,result);
        }
        if(next!=null){
            next.afterCharge(event,result);
        }
    }


    protected  abstract void doBeforeCharge(ChargeEvent event) throws IllegalChargeException;
    protected  abstract void doAfterCharge(ChargeEvent event,ChargeResult result) throws IllegalChargeException;

}

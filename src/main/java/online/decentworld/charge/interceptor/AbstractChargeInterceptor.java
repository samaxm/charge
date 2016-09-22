package online.decentworld.charge.interceptor;

import online.decentworld.charge.charger.ChargeResult;
import online.decentworld.charge.event.ChargeEvent;

/**
 * Created by Sammax on 2016/9/22.
 */
public abstract class AbstractChargeInterceptor implements  ChargeInterceptor {


    private ChargeInterceptor next;

    @Override
    public ChargeInterceptor then(ChargeInterceptor next) {
        this.next=next;
        return next;
    }

    @Override
    public void beforeCharge(ChargeEvent event) throws IllegalChargeException {
        doBeforeCharge(event);
        if(next!=null){
            next.beforeCharge(event);
        }
    }


    @Override
    public void afterCharge(ChargeResult result) throws IllegalChargeException  {
        doAfterCharge(result);
        if(next!=null){
            next.afterCharge(result);
        }
    }


    protected  abstract void doBeforeCharge(ChargeEvent event) throws IllegalChargeException;
    protected  abstract void doAfterCharge(ChargeResult result) throws IllegalChargeException;

}

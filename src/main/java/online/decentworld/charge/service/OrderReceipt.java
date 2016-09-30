package online.decentworld.charge.service;

/**
 * Created by Sammax on 2016/9/26.
 */
public class OrderReceipt {
    private String orderNum;
    private Object requestData;
    public OrderReceipt(){}
    public OrderReceipt(String orderNum, Object requestData) {
        this.orderNum = orderNum;
        this.requestData = requestData;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Object getRequestData() {
        return requestData;
    }

    public void setRequestData(Object requestData) {
        this.requestData = requestData;
    }
}

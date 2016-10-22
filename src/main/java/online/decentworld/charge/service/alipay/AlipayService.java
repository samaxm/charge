package online.decentworld.charge.service.alipay;


import online.decentworld.charge.service.ThridPartyReqestCreator;
import online.decentworld.rdb.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.URLEncoder;


public class AlipayService implements ThridPartyReqestCreator{

	private static Field[] fileds=null;
	private static Logger log= LoggerFactory.getLogger(AlipayService.class);
	static{
		fileds= AlipayPayInfo.class.getDeclaredFields();
		for(Field f:fileds){
			f.setAccessible(true);
		}

	}

	@Override
	public Object getRequestData(Order order, String ip, String msg) throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append("_input_charset=").append(AlipayConfiguration.input_charset).append("&body=")
				.append(order.getDwid()).append("&notify_url=").append(AlipayConfiguration.notify_url)
				.append("&out_trade_no=").append(order.getOrdernumer()).append("&partner=")
				.append(AlipayConfiguration.partner).append("&payment_type=").append(AlipayConfiguration.payment_type)
				.append("&seller_id=").append(AlipayConfiguration.sellerID).append("&service=").append(AlipayConfiguration.mobile_pay_service)
				.append("&subject=").append(msg).append("&total_fee=").append(String.valueOf(order.getAmount()/100d));//阿里单位为元
		String sign;
		try {
			sign=SignUtils.sign(sb.toString(), AlipayConfiguration.dw_private_key);
			sign = URLEncoder.encode(sign,AlipayConfiguration.input_charset);
			sb.append("&sign=").append(sign).append("&sign_type=").append(AlipayConfiguration.sign_type);
			log.debug("[SIGN]#"+sign);
		} catch (Exception e) {
			log.warn("",e);
		}
		return sb.toString();
	}


	public static void main(String[] args) {
		double a=15/100d;
		System.out.println(a);
	}
}

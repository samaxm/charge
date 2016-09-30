package online.decentworld.charge.service.wx;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import online.decentworld.charge.service.ThridPartyReqestCreator;
import online.decentworld.rdb.entity.Order;
import online.decentworld.tools.MD5;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class WXPayService implements ThridPartyReqestCreator{
	

	private static Field[] fileds=null;
	private static Logger log= LoggerFactory.getLogger(WXPayService.class);
	static{
		fileds= WXPayInfo.class.getDeclaredFields();
		for(Field f:fileds){
			f.setAccessible(true);
		}
		
	}
	private WXPayInfo createPayInfo(Order order,String ip,String msg){
		WXPayInfo payInfo=new WXPayInfo();
		 payInfo.setAppid(WXConfig.appid);
		  payInfo.setMch_id(WXConfig.mch_id);
		  payInfo.setNonce_str(create_nonce_str());
		  payInfo.setBody(msg);
		  payInfo.setOut_trade_no(order.getOrdernumer());
		  payInfo.setAttach(ip);
		  //单位为分
		  JSONObject attach=new JSONObject();
		  attach.put("dwID",order.getDwid());
		  payInfo.setAttach(attach.toJSONString());
		  payInfo.setTotal_fee(String.valueOf(order.getAmount()));
		  payInfo.setSpbill_create_ip(ip);
		  payInfo.setNotify_url(WXConfig.notify_url);
		  payInfo.setTrade_type(WXConfig.trade_type);
		  payInfo.setNotify_url(WXConfig.notify_url);
		return payInfo;
	}
	
	 private static String create_nonce_str() {
		 return UUID.randomUUID().toString().replace("-","");
	 } 
	
	 private String getSign(WXPayInfo payInfo) throws Exception {
		  String signTemp = "appid="+payInfo.getAppid()
		   +"&attach="+payInfo.getAttach()
		   +"&body="+payInfo.getBody()
		   +"&mch_id="+payInfo.getMch_id()
		   +"&nonce_str="+payInfo.getNonce_str()
		   +"&notify_url="+payInfo.getNotify_url()
		   +"&out_trade_no="+payInfo.getOut_trade_no()
		   +"&spbill_create_ip="+payInfo.getSpbill_create_ip()
		   +"&total_fee="+payInfo.getTotal_fee()
		   +"&trade_type="+payInfo.getTrade_type()
		   +"&key="+WXConfig.key; //这个key注意
		 	log.debug("String#"+signTemp);
		  return  MD5.GetMD5Code(signTemp).toUpperCase();
	}
	 
	 
	private String transferToXML(WXPayInfo payInfo) throws IllegalArgumentException, IllegalAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("<xml>").append("\n");
		for(Field f:fileds){
			String value=(String)f.get(payInfo);
			String name=f.getName();
			if(value!=null&&!value.equals("")){
				sb.append("<").append(name).append(">").append("<![CDATA[").append(value).append("]]>").append("</").append(name).append(">").append("\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	} 
	
	private String createRequestXML(Order order,String ip,String msg) throws Exception{
		WXPayInfo info=createPayInfo(order, ip,msg);
		String sign=getSign(info);
		info.setSign(sign);
		String xml=transferToXML(info);
		return xml;
	}
	

	 private StringBuffer httpsRequest(String address,String output)
	  throws Exception {
	  URL url = new URL(address);
	  HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	  connection.setDoOutput(true);
	  connection.setDoInput(true);
	  connection.setUseCaches(false);
	  connection.setRequestMethod("GET");
	  if (null != output) {
	  OutputStream outputStream = connection.getOutputStream();
	  outputStream.write(output.getBytes("UTF-8"));
	  outputStream.close();
	  }
	  // 从输入流读取返回内容
	  InputStream inputStream = connection.getInputStream();
	  InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	  String str = null;
	  StringBuffer buffer = new StringBuffer();
	  while ((str = bufferedReader.readLine()) != null) {
	  buffer.append(str);
	  }
	  bufferedReader.close();
	  inputStreamReader.close();
	  inputStream.close();
	  inputStream = null;
	  connection.disconnect();
	  return buffer;
	 }
	 
	 private Map<String, String> parseXml(String xml) throws Exception {
		  Map<String, String> map = new HashMap<String, String>();
		  Document document = DocumentHelper.parseText(xml);
		  Element root = document.getRootElement();
		  List<Element> elementList = root.elements();
		  for (Element e : elementList)
		  map.put(e.getName(), e.getText());
		  return map;
	}
	 
	 private String getPrepay_id(Order order,String ip,String msg) throws Exception{
		 String xml=createRequestXML(order, ip,msg);
		 log.debug("xml:"+xml);
		 StringBuffer sb=httpsRequest(WXConfig.unifiedorder_url,xml);
		 System.out.println("return "+sb.toString());
		 Map<String, String> map=parseXml(sb.toString());
		 String prepay_id=map.get("prepay_id");
		 if(prepay_id==null){
			 throw new Exception("签名错误！");
		 }
		 log.debug(prepay_id);
		 return prepay_id;
	 }
	 
	 public static void main(String[] args) throws Exception {
		WXPayService service=new WXPayService();
		Order order=new Order();
		order.setOrdernumer("15122217490015");
		order.setExtra("test");
		order.setAmount(1);
		service.getPrepay_id(order, "112.74.13.117","");
	}
	
	 private String signReturnMsg(HashMap<String,String> map){
		 StringBuffer sb=new StringBuffer();
		 sb.append("appid=").append(map.get("appid")).append("&noncestr=")
		 .append(map.get("noncestr")).append("&package=").append(map.get("package"))
		 .append("&partnerid=").append(map.get("partnerid")).append("&prepayid=")
		 .append(map.get("prepayid")).append("&timestamp=").append(map.get("timestamp"))
		 .append("&key=").append(WXConfig.key);
		 System.out.println(sb.toString());
		 return  MD5.GetMD5Code(sb.toString()).toUpperCase();
	 }
	 
	 

	
	public String getOpenID(String code) throws Exception{
		StringBuilder sb=new StringBuilder();
		sb.append(WXConfig.access_token_url).append("?appid=").append(WXConfig.appid).append("&secret=")
		.append(WXConfig.app_secret).append("&code=").append(code).append("&grant_type=authorization_code");
		StringBuffer result=httpsRequest(sb.toString(),null);
		return (String) JSON.parseObject(result.toString()).get("openid");
	}

	public Object getRequestData(Order order,String ip,String msg) throws Exception {
		HashMap<String,String> map=new HashMap<String, String>();
		String prepayID=getPrepay_id(order, ip,msg);
		map.put("appid", WXConfig.appid);
		map.put("partnerid", WXConfig.mch_id);
		map.put("prepayid", prepayID);
		map.put("package", WXConfig.packageStr);
		map.put("noncestr", create_nonce_str());
		map.put("timestamp", String.valueOf(new Date().getTime()/1000));
		String sign=signReturnMsg(map);
		if(sign==null){
			throw new Exception("sign error");
		}
		map.put("sign", sign);
		return map;
	}
}

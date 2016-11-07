package online.decentworld.charge.service.wx;


import okhttp3.*;
import online.decentworld.charge.service.TransferServiceTemplate;
import online.decentworld.charge.service.TransferStatus;
import online.decentworld.tools.MD5;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WXTransferService extends TransferServiceTemplate
{

	private static Field[] fileds=null;
	private static Logger log= LoggerFactory.getLogger(WXTransferService.class);

	private static OkHttpClient httpclient;
	static{
		fileds= WXTransferInfo.class.getDeclaredFields();
		for(Field f:fileds){
			f.setAccessible(true);
		}
		//init certicate
		//指定读取证书格式为PKCS12 
		try{
			X509TrustManager x509m = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					X509Certificate[] cArrr = new X509Certificate[0];
					return cArrr;
				}

				@Override
				public void checkServerTrusted(final X509Certificate[] chain,
											   final String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(final X509Certificate[] chain,
											   final String authType) throws CertificateException {
				}
			};
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		//读取本机存放PKCS12证书文件
		FileInputStream instream = new FileInputStream(new File(WXConfig.certificate));
		try {
		//指定PKCS12的密码
		keyStore.load(instream, WXConfig.mch_id.toCharArray());
//		System.setProperty("javax.net.ssl.trustStore","clientTrustStore.key");
//		System.setProperty("javax.net.ssl.trustStorePassword",WXConfig.mch_id);
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContext.getInstance("TLSv1");
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, WXConfig.mch_id.toCharArray());
		sslcontext.init(keyManagerFactory.getKeyManagers(),new TrustManager[]{x509m},new SecureRandom());
		SSLSocketFactory sslSocketFactory=sslcontext.getSocketFactory();
		httpclient = new OkHttpClient().newBuilder().sslSocketFactory(sslSocketFactory).build();
		}catch(Exception ex){
			ex.printStackTrace();
		}
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

	public TransferStatus createTransfer(int amount,String descipe,String openid,String transferNum,String ip) throws Exception{
		WXTransferInfo info=new WXTransferInfo();
		if(amount<1){
			return TransferStatus.NOTENOUGH;
		}
		info.setAmount(String.valueOf((int)(amount*100)));
		info.setCheck_name("NO_CHECK");
		info.setDesc(descipe);
		info.setMch_appid(WXConfig.appid);
		info.setMchid(WXConfig.mch_id);
		String nonce_str=create_nonce_str();
		info.setNonce_str(nonce_str);
		info.setOpenid(openid);
		info.setPartner_trade_no(transferNum);
		info.setSpbill_create_ip(ip);
		String sign=getSign(info);
		info.setSign(sign);
		String reqString=transferToXML(info);
//		System.out.println(reqString);
		RequestBody body=RequestBody.create(MediaType.parse("text/html;charset=UTF-8"),reqString);
		Request request=new Request.Builder().url(WXConfig.transfer_url).post(body).build();
		Response response=httpclient.newCall(request).execute();
//		CloseableHttpResponse response=httpclient.execute(post);
		Map<String, String> map=parseXml(response.body().string());
		if("SUCCESS".equals(map.get("return_code"))){
			return TransferStatus.SUCCESS;
		}else{
			log.error("transfer failed transferNum:"+transferNum+"	transfer failed msg:"+map.get("return_msg")
			+"	transfer failed code:"+map.get("err_code"));
			return TransferStatus.FAILED;
		}
	}

	@Override
	protected TransferStatus check(int amount) {
		if(amount<1){
			return TransferStatus.NOTENOUGH;
		}
		return TransferStatus.SUCCESS;
	}

	private String transferToXML(WXTransferInfo payInfo) throws IllegalArgumentException, IllegalAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("<xml>").append("\n");
		for(Field f:fileds){
			String value=(String)f.get(payInfo);
			String name=f.getName();
			if(value!=null&&value.length()!=0){
				sb.append("<").append(name).append(">").append(value).append("</").append(name).append(">").append("\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	} 
	
	 private static String create_nonce_str() {
		 return UUID.randomUUID().toString().replace("-","");
	 } 
	 
	 private String getSign(WXTransferInfo info){
		 StringBuffer sb=new StringBuffer();
		 sb.append("amount=").append(info.getAmount()).append("&check_name=").append(info.getCheck_name())
		 .append("&desc=").append(info.getDesc()).append("&mch_appid=").append(info.getMch_appid()).append("&mchid=")
		 .append(info.getMchid()).append("&nonce_str=").append(info.getNonce_str()).append("&openid=").append(info.getOpenid())
		 .append("&partner_trade_no=").append(info.getPartner_trade_no()).append("&spbill_create_ip=").append(info.getSpbill_create_ip())
		 .append("&key=").append(WXConfig.key);
		  return  MD5.GetMD5Code(sb.toString()).toUpperCase();
	 }

	 public static void main(String[] args) throws Exception {

		 WXTransferService service=new WXTransferService();
		 service.createTransfer(1,"123","oiYBlwmYqwXLg-ke5uU371rRyqaU","123123","127.74.13.117");
	}


}

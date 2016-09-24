package online.decentworld.charge.service.wx;

public class WXConfig {
	public static String appid="wx8a6304437033f400";
	public static String mch_id="1282167201";
	public static String notify_url="http://120.76.26.75/DecentWorldServer/charge/getWXOrderResponse";
	public static String trade_type="APP";
	public static String key="0nhgdsoupnjdd49oeenskncfmdk9rvei";
	public static String unifiedorder_url="https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token";
	public static String transfer_url="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	public static String packageStr="Sign=WXPay";
	public static String app_secret="d4624c36b6795d1d99dcf0547af5443d";
	public static String SUCCESS="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	public static int least_fee=1;
	public static int most_fee=25;
	public static float fee_percent=0.01f;
	public static String certificate="/home/certificate/apiclient_cert.p12";
	public static String getUserInfo="https://api.weixin.qq.com/sns/userinfo";
}

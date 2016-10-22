package online.decentworld.charge.service.alipay;

import online.decentworld.charge.ChargeConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Sammax on 2016/9/26.
 */
public class AlipayConfiguration {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088121118480062";
    // 商户的私钥
    public static String md5_key = "jktpfel9pa85604ho9fsc3ujmw0htntt";

    // 支付宝的公钥，无需修改该值
    public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    public static String payment_type="1";
    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "D:\\";
//    public static String notify_url="http://120.76.26.75/DecentWorldServer/charge/getOrderResponse";
    public static String notify_url="http://http://dev.service.dawan.online/face2face/wealth/response/alipay";
    public static String notify_url_transfer="http://120.76.26.75:8888/DecentWorldServer/charge/getTransferResponse";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";
    public static String sellerID="95372537@qq.com";
    public static String account_name="深圳圣贤御品堂实业有限公司";
    // 签名方式 不需修改
    public static String sign_type = "RSA";
    public static String sign_type_MD5 = "MD5";

    public static int least_fee=1;
    public static int most_fee=25;
    public static float fee_percent=0.005f;
    public static String dw_public_key = null;
    public static String dw_private_key=null;
    public static String mobile_pay_service="mobile.securitypay.pay";

    static{
        File privateKeyFile=new File(ChargeConfig.RSA_PRIVATE);
        File publicKeyFile=new File(ChargeConfig.RSA_PUBLIC);
        FileInputStream fis=null;
        FileInputStream pub_fis=null;
        try {
            fis = new FileInputStream(privateKeyFile);
            StringBuffer sb=new StringBuffer();
            int c;
            while((c=fis.read())!=-1){
                sb.append((char)c);
            }
            dw_private_key=sb.toString().replaceAll("(-+BEGIN PRIVATE KEY-+\\r?\\n?|-+END PRIVATE KEY-+\\r?\\n?)", "");
            System.out.println(dw_private_key);
            pub_fis=new FileInputStream(publicKeyFile);
            sb.setLength(0);
            while((c=pub_fis.read())!=-1){
                sb.append((char)c);
            }
            dw_public_key=sb.toString().replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n?|-+END PUBLIC KEY-+\\r?\\n?)", "");
            System.out.println(dw_public_key);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(pub_fis!=null){
                try {
                    pub_fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(dw_private_key);

    }
}

package online.decentworld.charge.service.wx;

import online.decentworld.tools.MD5;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WXSignUtil {
	public static String sign(Map<String, String> map){
		StringBuffer sb=new StringBuffer();
		List<String> list=new LinkedList<String>();
		for(String key:map.keySet()){
			list.add(key);		
		}
		Collections.sort(list);
		System.out.println(list);
		for(String key:list){
			if(key.equals("sign")){
				continue;
			}
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		sb.append("key=").append(WXConfig.key);
		System.out.println(sb.toString());
		return MD5.GetMD5Code(sb.toString()).toUpperCase();
	}
}

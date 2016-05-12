package me.xucan.drive.util;

import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	public final static int ERROR_PARAM_NULL = 100;
	public final static int ERROR_PARAM_INVALID = 200;
	public final static int ERROR_500 = 500;
	public final static int ERROR_200 = 200;
	
	public final static int ERROR_PHONE_EXIST = 101;
	
	/**
	 * ����json��ʽ���������
	 * @param map
	 * @return
	 */
	public static String createJson(Map<String, Object> map){
		Set<String> keySet = map.keySet();
		JSONObject json = new JSONObject();
		if(keySet.contains("status")){
			int status = (int) map.get("status");
			json.put("status",status);
			if(status == 200){
				JSONObject body = new JSONObject();
				for(String key : keySet){
					if(!key.equals("status"))
						body.put(key, map.get(key));
				}
				json.put("body", body);
			}else if(keySet.contains("errorMsg")){
				json.put("errorMsg",(String)map.get("errorMsg"));
			}
		
		}else{
			json.put("status",ERROR_500);
			json.put("errorMsg","");
		}
		return json.toJSONString();
	}
}

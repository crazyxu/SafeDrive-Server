package me.xucan.drive.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import me.xucan.drive.bean.DriveEvent;
import me.xucan.drive.bean.FormatType;
import me.xucan.drive.bean.SdkHttpResult;
import me.xucan.drive.bean.WarnMessage;
import me.xucan.drive.net.ApiHttpClient;

public class MessageUtil {
	public static void pushMessage(String userId, JSONObject object){
		List<String> ids = new ArrayList<>();
		ids.add(userId);
		//发送系统消息
		try {
			SdkHttpResult result = ApiHttpClient.publishSystemMessage(ServerConstant.RC_KEY, ServerConstant.RC_SCRET, "22",
					ids, new WarnMessage(object.toJSONString()), JSON.toJSONString(object.toJSONString()),
					"", FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

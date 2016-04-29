package me.xucan.drive.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

import me.xucan.drive.bean.DriveEvent;
import me.xucan.drive.bean.FormatType;
import me.xucan.drive.bean.SdkHttpResult;
import me.xucan.drive.bean.WarnMessage;
import me.xucan.drive.net.ApiHttpClient;

public class MessageUtil {
	public static void pushMessage(List<String> userIds, DriveEvent event){
		//发送系统消息
		try {
			SdkHttpResult result = ApiHttpClient.publishSystemMessage(ServerConstant.RC_KEY, ServerConstant.RC_SCRET, "system",
					userIds, new WarnMessage("txtMessagehaha"), JSON.toJSONString(event),
					"", FormatType.json);
			System.out.println(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

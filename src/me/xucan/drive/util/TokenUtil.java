package me.xucan.drive.util;


import me.xucan.drive.bean.FormatType;
import me.xucan.drive.bean.SdkHttpResult;
import me.xucan.drive.bean.User;
import me.xucan.drive.net.ApiHttpClient;

public class TokenUtil {
	
	/**
	 * 获取token
	 * @param user
	 * @return
	 */
	public static String getToken(User user){
		try {
			SdkHttpResult result = ApiHttpClient.getToken(ServerConstant.RC_KEY, ServerConstant.RC_SCRET, String.valueOf(user.getUserId()), user.getUserName(),
					user.getPortraitUri(), FormatType.json);
			return result.toString();				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

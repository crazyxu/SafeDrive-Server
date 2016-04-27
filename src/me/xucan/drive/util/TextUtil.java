package me.xucan.drive.util;

public class TextUtil {
	
	public final static boolean isNotEmpty(String txt){
		if(txt != null && !txt.equals("")){
			return true;
		}
		return false;
	}
	
	public final static boolean isEmpty(String txt){
		if(txt == null || txt.equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * �ж϶�������Ƿ�Ϊ��
	 * @param strings
	 * @return
	 */
	public final static boolean isNotEmpty(String ...strings){
		for(String str : strings){
			if(isEmpty(str)){
				return false;
			}
		}
		return true;
	}
}

package me.xucan.drive.bean;

import com.alibaba.fastjson.JSON;

//文本消息
public class WarnMessage extends Message {

	private String content;


	
	public WarnMessage(String content) {
		this.type = "SD:DriveWarnMsg";
		this.content = content;
	}

	public WarnMessage(String content,String extra) {
		this(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

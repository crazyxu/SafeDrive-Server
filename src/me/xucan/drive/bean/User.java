package me.xucan.drive.bean;

public class User {
	private String userName;
	private String password;
	private String phone;
	private int userId;
	private String portraitUri;
	private String token;
	private String urgentPhone;
	public String getUrgentPhone() {
		return urgentPhone;
	}
	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String userPwd) {
		this.password = userPwd;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPortraitUri() {
		return portraitUri;
	}
	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}

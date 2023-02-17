package com.thisteam.dangdangeat.vo;

/*
  이메일 인증을 위한 정보를 담을 AuthVO 객체
 */
public class AuthVO {
	private String auth_id;
	private String auth_code;
	
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	
	@Override
	public String toString() {
		return "AuthVO [auth_id=" + auth_id + ", auth_code=" + auth_code + "]";
	}
	
	
}















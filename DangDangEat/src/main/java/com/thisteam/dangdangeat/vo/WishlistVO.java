package com.thisteam.dangdangeat.vo;

/*
  
 */
public class WishlistVO {
	
	private String member_id;
	private int pro_code;
	private int ischecked;
	private String pro_name;
	private int pro_price;
	private String pro_real_thumb;
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getPro_code() {
		return pro_code;
	}
	public void setPro_code(int pro_code) {
		this.pro_code = pro_code;
	}
	public int getIschecked() {
		return ischecked;
	}
	public void setIschecked(int ischecked) {
		this.ischecked = ischecked;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public int getPro_price() {
		return pro_price;
	}
	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}
	public String getPro_real_thumb() {
		return pro_real_thumb;
	}
	public void setPro_real_thumb(String pro_real_thumb) {
		this.pro_real_thumb = pro_real_thumb;
	}
	@Override
	public String toString() {
		return "WishlistVO [member_id=" + member_id + ", pro_code=" + pro_code + ", ischecked=" + ischecked
				+ ", pro_name=" + pro_name + ", pro_price=" + pro_price + ", pro_real_thumb=" + pro_real_thumb + "]";
	}
	
	
	
	
}















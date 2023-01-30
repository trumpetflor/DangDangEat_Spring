package com.thisteam.dangdangeat.vo;

/*
  
 */
public class CartVO {
	
	private String cart_code;
	private String member_id;
	private int pro_code;
	private int cart_amount;
	private int cart_ischecked;
	private String pro_name;
	private int pro_price;
	private String pro_real_thumb;
	private int totalPrice;
	
	public String getCart_code() {
		return cart_code;
	}
	public void setCart_code(String cart_code) {
		this.cart_code = cart_code;
	}
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
	public int getCart_amount() {
		return cart_amount;
	}
	public void setCart_amount(int cart_amount) {
		this.cart_amount = cart_amount;
	}
	public int getCart_ischecked() {
		return cart_ischecked;
	}
	public void setCart_ischecked(int cart_ischecked) {
		this.cart_ischecked = cart_ischecked;
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
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "CartVO [cart_code=" + cart_code + ", member_id=" + member_id + ", pro_code=" + pro_code
				+ ", cart_amount=" + cart_amount + ", cart_ischecked=" + cart_ischecked + ", pro_name=" + pro_name
				+ ", pro_price=" + pro_price + ", pro_real_thumb=" + pro_real_thumb + ", totalPrice=" + totalPrice
				+ "]";
	}
	
	
	
}















package com.thisteam.dangdangeat.vo;

//장바구니의 상품에 대한 정보를 가져오는 view (cart_product_view)

public class CartProductVO {
	
	private int cart_code; // 장바구니 코드
	private String member_id; // 주문회원 아이디
	private int pro_code; // 장바구니에 담긴 상품코드
	private int cart_amount; // 장바구니 수량
	private String pro_name; // 장바구니 상품명
	private String pro_brand; // 장바구니 상품브랜드
	private int pro_price; // 장바구니 상품가격
	private String pro_real_thumb; // 장바구니 상품썸네일
	public int getCart_code() {
		return cart_code;
	}
	public void setCart_code(int cart_code) {
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
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_brand() {
		return pro_brand;
	}
	public void setPro_brand(String pro_brand) {
		this.pro_brand = pro_brand;
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
		return "CartProductVO [cart_code=" + cart_code + ", member_id=" + member_id + ", pro_code=" + pro_code
				+ ", cart_amount=" + cart_amount + ", pro_name=" + pro_name + ", pro_brand=" + pro_brand
				+ ", pro_price=" + pro_price + ", pro_real_thumb=" + pro_real_thumb + "]";
	}
	
}















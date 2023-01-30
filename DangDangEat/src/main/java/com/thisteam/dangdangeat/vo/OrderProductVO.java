package com.thisteam.dangdangeat.vo;

//  주문 상품 테이블 : 세미 댕댕잇 프로젝트와 일치

public class OrderProductVO {
	
	private int order_code;// 주문번호(fk)
	private int pro_code; //상품번호(fk)
	private int order_stock; //주문수량
	private int cart_code; // 카트 코드
	
	public int getOrder_code() {
		return order_code;
	}
	public void setOrder_code(int order_code) {
		this.order_code = order_code;
	}
	public int getPro_code() {
		return pro_code;
	}
	public void setPro_code(int pro_code) {
		this.pro_code = pro_code;
	}
	public int getOrder_stock() {
		return order_stock;
	}
	public void setOrder_stock(int order_stock) {
		this.order_stock = order_stock;
	}
	public int getCart_code() {
		return cart_code;
	}
	public void setCart_code(int cart_code) {
		this.cart_code = cart_code;
	}
	
	@Override
	public String toString() {
		return "OrderProductVO [order_code=" + order_code + ", pro_code=" + pro_code + ", order_stock=" + order_stock
				+ ", cart_code=" + cart_code + "]";
	}
	
	
}















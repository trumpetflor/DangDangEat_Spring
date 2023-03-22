package com.thisteam.dangdangeat.vo;

import java.util.Arrays;

//  복수상품 구매를 위한 배열 타입의 테이블 생성

public class OrderProductArrVO {
	
	private int[] order_code;//   주문번호(fk)
	private int[] pro_code; //    상품번호(fk)
	private int[] order_stock; // 주문수량
	
	public int[] getOrder_code() {
		return order_code;
	}
	public void setOrder_code(int[] order_code) {
		this.order_code = order_code;
	}
	public int[] getPro_code() {
		return pro_code;
	}
	public void setPro_code(int[] pro_code) {
		this.pro_code = pro_code;
	}
	public int[] getOrder_stock() {
		return order_stock;
	}
	public void setOrder_stock(int[] order_stock) {
		this.order_stock = order_stock;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderProductArrVO [order_code=").append(Arrays.toString(order_code)).append(", pro_code=")
				.append(Arrays.toString(pro_code)).append(", order_stock=").append(Arrays.toString(order_stock))
				.append("]");
		return builder.toString();
	}
	
}















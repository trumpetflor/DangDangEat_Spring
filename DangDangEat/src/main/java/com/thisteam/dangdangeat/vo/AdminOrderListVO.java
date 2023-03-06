package com.thisteam.dangdangeat.vo;

import java.sql.Date;

// 관리자 페이지 - 주문 관리 페이지를 위한 VO 

public class AdminOrderListVO {
	
	private int order_code;
	private String member_id;
	private String order_name;
	private String order_postcode;
	private String order_address1;
	private String order_address2;
	private String order_mobile;
	private String order_comment;
	private int order_status;
	private Date order_date;
	private int pay_number;
	private int pay_amount;
	public int getOrder_code() {
		return order_code;
	}
	public void setOrder_code(int order_code) {
		this.order_code = order_code;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getOrder_postcode() {
		return order_postcode;
	}
	public void setOrder_postcode(String order_postcode) {
		this.order_postcode = order_postcode;
	}
	public String getOrder_address1() {
		return order_address1;
	}
	public void setOrder_address1(String order_address1) {
		this.order_address1 = order_address1;
	}
	public String getOrder_address2() {
		return order_address2;
	}
	public void setOrder_address2(String order_address2) {
		this.order_address2 = order_address2;
	}
	public String getOrder_mobile() {
		return order_mobile;
	}
	public void setOrder_mobile(String order_mobile) {
		this.order_mobile = order_mobile;
	}
	public String getOrder_comment() {
		return order_comment;
	}
	public void setOrder_comment(String order_comment) {
		this.order_comment = order_comment;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getPay_number() {
		return pay_number;
	}
	public void setPay_number(int pay_number) {
		this.pay_number = pay_number;
	}
	public int getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(int pay_amount) {
		this.pay_amount = pay_amount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdminOrderListVO [order_code=").append(order_code).append(", member_id=").append(member_id)
				.append(", order_name=").append(order_name).append(", order_postcode=").append(order_postcode)
				.append(", order_address1=").append(order_address1).append(", order_address2=").append(order_address2)
				.append(", order_mobile=").append(order_mobile).append(", order_comment=").append(order_comment)
				.append(", order_status=").append(order_status).append(", order_date=").append(order_date)
				.append(", pay_number=").append(pay_number).append(", pay_amount=").append(pay_amount).append("]");
		return builder.toString();
	}
	
	
}

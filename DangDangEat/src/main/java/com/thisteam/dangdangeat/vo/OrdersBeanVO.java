package com.thisteam.dangdangeat.vo;

import java.sql.Date;

// orders 테이블 : 세미 댕댕잇 프로젝트와 일치
 
public class OrdersBeanVO {
	
	private int order_code; //주문번호
	private String member_id;
	private String order_name;
	private String order_postcode;
	private String order_address1;
	private String order_address2;
	private String order_mobile;
	private String order_comment;
	private int order_status; // 주문상태 (string으로 변경 예정)
	private Date order_date;
	
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
	
	@Override
	public String toString() {
		return "OrdersBeanVO [order_code=" + order_code + ", member_id=" + member_id + ", order_name=" + order_name
				+ ", order_postcode=" + order_postcode + ", order_address1=" + order_address1 + ", order_address2="
				+ order_address2 + ", order_mobile=" + order_mobile + ", order_comment=" + order_comment
				+ ", order_status=" + order_status + ", order_date=" + order_date + "]";
	}
	
}















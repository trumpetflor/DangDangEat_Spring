package com.thisteam.dangdangeat.vo;

public class Coupon_viewVO {
//	cp_code	varchar(30)
//	cp_name	varchar(30)
//	cp_discount_value	int
//	cp_min_price	int
//	cp_max_discount	int
//	cp_target	varchar(15)
//	cp_startdate	varchar(10)
//	cp_period	int
//	cp_enddate	varchar(29)
//	cp_current_st	int
//	cp_status	int
	
	String cp_code;
	String cp_name;
	int cp_discount_value;
	int cp_min_price;
	int cp_max_discount;
	String cp_target;
	String cp_startdate;
	int cp_period;
	String cp_enddate;
	int cp_current_st;
	int cp_status;
	
	public String getCp_code() {
		return cp_code;
	}
	public void setCp_code(String cp_code) {
		this.cp_code = cp_code;
	}
	public String getCp_name() {
		return cp_name;
	}
	public void setCp_name(String cp_name) {
		this.cp_name = cp_name;
	}
	public int getCp_discount_value() {
		return cp_discount_value;
	}
	public void setCp_discount_value(int cp_discount_value) {
		this.cp_discount_value = cp_discount_value;
	}
	public int getCp_min_price() {
		return cp_min_price;
	}
	public void setCp_min_price(int cp_min_price) {
		this.cp_min_price = cp_min_price;
	}
	public int getCp_max_discount() {
		return cp_max_discount;
	}
	public void setCp_max_discount(int cp_max_discount) {
		this.cp_max_discount = cp_max_discount;
	}
	public String getCp_target() {
		return cp_target;
	}
	public void setCp_target(String cp_target) {
		this.cp_target = cp_target;
	}
	public String getCp_startdate() {
		
		return cp_startdate;
	}
	public void setCp_startdate(String cp_startdate) {
		this.cp_startdate = cp_startdate;
	}
	public int getCp_period() {
		return cp_period;
	}
	public void setCp_period(int cp_period) {
		this.cp_period = cp_period;
	}
	public String getCp_enddate() {
		if(cp_enddate == null || cp_enddate == "") {
//			System.out.println("getCp_enddate 실행됨 !!!!!!!!!!!");
			cp_enddate = "-";
		}
		return cp_enddate;
	}
	public void setCp_enddate(String cp_enddate) {
		this.cp_enddate = cp_enddate;
	}
	public int getCp_current_st() {
		return cp_current_st;
	}
	public void setCp_current_st(int cp_current_st) {
		this.cp_current_st = cp_current_st;
	}
	public int getCp_status() {
		return cp_status;
	}
	public void setCp_status(int cp_status) {
		this.cp_status = cp_status;
	}
	
	@Override
	public String toString() {
		return "Coupon_viewVO [cp_code=" + cp_code + ", cp_name=" + cp_name + ", cp_discount_value=" + cp_discount_value
				+ ", cp_min_price=" + cp_min_price + ", cp_max_discount=" + cp_max_discount + ", cp_target=" + cp_target
				+ ", cp_startdate=" + cp_startdate + ", cp_period=" + cp_period + ", cp_enddate=" + cp_enddate
				+ ", cp_current_st=" + cp_current_st + ", cp_status=" + cp_status + "]";
	}

	
	
}

package com.thisteam.dangdangeat.vo;

public class Mc_viewVO {
	String member_id;
	String cp_name;
	String cp_code;
	String cp_target;
	int cp_discount_value;
	int cp_min_price;
	int cp_max_discount;
	String cp_startdate;
	int cp_period;
	int cp_status;
	String mc_used;
	String target_sd;
	String target_ed;
	int mc_stat; //사용유무
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getCp_name() {
		return cp_name;
	}
	public void setCp_name(String cp_name) {
		this.cp_name = cp_name;
	}
	public String getCp_code() {
		return cp_code;
	}
	public void setCp_code(String cp_code) {
		this.cp_code = cp_code;
	}
	public String getCp_target() {
		return cp_target;
	}
	public void setCp_target(String cp_target) {
		this.cp_target = cp_target;
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
	public int getCp_status() {
		return cp_status;
	}
	public void setCp_status(int cp_status) {
		this.cp_status = cp_status;
	}
	public String getMc_used() {
		return mc_used;
	}
	public void setMc_used(String mc_used) {
		this.mc_used = mc_used;
	}
	public String getTarget_sd() {
		return target_sd;
	}
	public void setTarget_sd(String target_sd) {
		this.target_sd = target_sd;
	}
	public String getTarget_ed() {
		return target_ed;
	}
	public void setTarget_ed(String target_ed) {
		this.target_ed = target_ed;
	}
	public int getMc_stat() {
		return mc_stat;
	}
	public void setMc_stat(int mc_stat) {
		this.mc_stat = mc_stat;
	}
	
	@Override
	public String toString() {
		return "Mc_viewVO [member_id=" + member_id + ", cp_name=" + cp_name + ", cp_code=" + cp_code + ", cp_target="
				+ cp_target + ", cp_discount_value=" + cp_discount_value + ", cp_min_price=" + cp_min_price
				+ ", cp_max_discount=" + cp_max_discount + ", cp_startdate=" + cp_startdate + ", cp_period=" + cp_period
				+ ", cp_status=" + cp_status + ", mc_used=" + mc_used + ", target_sd=" + target_sd + ", target_ed="
				+ target_ed + ", mc_stat=" + mc_stat + "]";
	}
	
	
	
}

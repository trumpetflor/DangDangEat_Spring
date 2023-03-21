package com.thisteam.dangdangeat.vo;

import java.sql.Date;

public class Review_product_viewVO {
/*
create view review_product_view as
select r.member_id , r.review_code  , r.review_pass , r.review_subject , r.review_content , r.review_readcount ,r.review_date, p.pro_code, p.pro_name , p.pro_thumb  ,  p.pro_real_thumb 
from review r , product p
where r.pro_code = p.pro_code ; 

 */
	
	String member_id;
	int review_code;
	String review_pass;
	String review_subject;
	String review_content;
	int review_readcount;
	Date review_date;
	int pro_code;
	String pro_name;
	String pro_thumb;
	String pro_real_thumb;
	
	
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getReview_code() {
		return review_code;
	}
	public void setReview_code(int review_code) {
		this.review_code = review_code;
	}
	public String getReview_pass() {
		return review_pass;
	}
	public void setReview_pass(String review_pass) {
		this.review_pass = review_pass;
	}
	public String getReview_subject() {
		return review_subject;
	}
	public void setReview_subject(String review_subject) {
		this.review_subject = review_subject;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public int getReview_readcount() {
		return review_readcount;
	}
	public void setReview_readcount(int review_readcount) {
		this.review_readcount = review_readcount;
	}
	
	
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	public int getPro_code() {
		return pro_code;
	}
	public void setPro_code(int pro_code) {
		this.pro_code = pro_code;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_thumb() {
		return pro_thumb;
	}
	public void setPro_thumb(String pro_thumb) {
		this.pro_thumb = pro_thumb;
	}
	public String getPro_real_thumb() {
		return pro_real_thumb;
	}
	public void setPro_real_thumb(String pro_real_thumb) {
		this.pro_real_thumb = pro_real_thumb;
	}
	
	@Override
	public String toString() {
		return "Review_product_viewVO [member_id=" + member_id + ", review_code=" + review_code + ", review_pass="
				+ review_pass + ", review_subject=" + review_subject + ", review_content=" + review_content
				+ ", review_readcount=" + review_readcount + ", review_date=" + review_date + ", pro_code=" + pro_code
				+ ", pro_name=" + pro_name + ", pro_thumb=" + pro_thumb + ", pro_real_thumb=" + pro_real_thumb + "]";
	}

	
	
	
	
	
	
}

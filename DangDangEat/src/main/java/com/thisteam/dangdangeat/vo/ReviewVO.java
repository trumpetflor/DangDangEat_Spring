package com.thisteam.dangdangeat.vo;

import java.sql.Date;

/*
review_code	int	NO	PRI		
member_id	varchar(20)	YES	MUL		
review_pass	varchar(16)	YES			
review_subject	varchar(100)	YES			
review_content	varchar(2000)	YES			
review_file	varchar(200)	YES			
review_real_file	varchar(200)	YES			
review_re_ref	int	YES			
review_re_lev	int	YES			
review_re_seq	int	YES			
review_readcount	int	YES			
review_date	date	YES			
pro_code	int	YES	MUL		
 */

public class ReviewVO {

	private int review_code;
	private String member_id;
	private String review_pass;
	private String review_subject;
	private String review_content;
	private String review_file;
	private String review_real_file;
	private int review_re_ref;
	private int review_re_lev;
	private int review_re_seq;
	private int review_readcount;
	private Date review_date;
	private int pro_code;
	
	public int getReview_code() {
		return review_code;
	}
	public void setReview_code(int review_code) {
		this.review_code = review_code;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
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
	public String getReview_file() {
		return review_file;
	}
	public void setReview_file(String review_file) {
		this.review_file = review_file;
	}
	public String getReview_real_file() {
		return review_real_file;
	}
	public void setReview_real_file(String review_real_file) {
		this.review_real_file = review_real_file;
	}
	public int getReview_re_ref() {
		return review_re_ref;
	}
	public void setReview_re_ref(int review_re_ref) {
		this.review_re_ref = review_re_ref;
	}
	public int getReview_re_lev() {
		return review_re_lev;
	}
	public void setReview_re_lev(int review_re_lev) {
		this.review_re_lev = review_re_lev;
	}
	public int getReview_re_seq() {
		return review_re_seq;
	}
	public void setReview_re_seq(int review_re_seq) {
		this.review_re_seq = review_re_seq;
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
	
	@Override
	public String toString() {
		return "ReviewVO [review_code=" + review_code + ", member_id=" + member_id + ", review_pass=" + review_pass
				+ ", review_subject=" + review_subject + ", review_content=" + review_content + ", review_file="
				+ review_file + ", review_real_file=" + review_real_file + ", review_re_ref=" + review_re_ref
				+ ", review_re_lev=" + review_re_lev + ", review_re_seq=" + review_re_seq + ", review_readcount="
				+ review_readcount + ", review_date=" + review_date + ", pro_code=" + pro_code + "]";
	}
	
}

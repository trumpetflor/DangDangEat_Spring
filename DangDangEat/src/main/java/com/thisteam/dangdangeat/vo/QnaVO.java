package com.thisteam.dangdangeat.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

/*
 qna_code	int	NO	PRI
member_id	varchar(20)	YES	MUL
qna_pass	varchar(16)	YES	
qna_subject	varchar(100)	YES	
qna_content	varchar(2000)	YES	
qna_file	varchar(200)	YES	
qna_real_file	varchar(200)	YES	
qna_re_ref	int	YES	
qna_re_lev	int	YES	
qna_re_seq	int	YES	
qna_date	date	YES	
qna_secret	varchar(1)	YES	
pro_code	int	YES	MUL 
 */

public class QnaVO {
	
	private int qna_code;
	private String member_id;
	private String qna_pass;
	private String qna_subject;
	private String qna_content;
	private String qna_file;
	private String qna_real_file;
	private int qna_re_ref;
	private int qna_re_lev;
	private int qna_re_seq;
	private Date qna_date;
	private String qna_secret;
	private int pro_code;
	private MultipartFile qna_image;
	
	public int getQna_code() {
		return qna_code;
	}
	public void setQna_code(int qna_code) {
		this.qna_code = qna_code;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getQna_pass() {
		return qna_pass;
	}
	public void setQna_pass(String qna_pass) {
		this.qna_pass = qna_pass;
	}
	public String getQna_subject() {
		return qna_subject;
	}
	public void setQna_subject(String qna_subject) {
		this.qna_subject = qna_subject;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public String getQna_file() {
		return qna_file;
	}
	public void setQna_file(String qna_file) {
		this.qna_file = qna_file;
	}
	public String getQna_real_file() {
		return qna_real_file;
	}
	public void setQna_real_file(String qna_real_file) {
		this.qna_real_file = qna_real_file;
	}
	public int getQna_re_ref() {
		return qna_re_ref;
	}
	public void setQna_re_ref(int qna_re_ref) {
		this.qna_re_ref = qna_re_ref;
	}
	public int getQna_re_lev() {
		return qna_re_lev;
	}
	public void setQna_re_lev(int qna_re_lev) {
		this.qna_re_lev = qna_re_lev;
	}
	public int getQna_re_seq() {
		return qna_re_seq;
	}
	public void setQna_re_seq(int qna_re_seq) {
		this.qna_re_seq = qna_re_seq;
	}
	public Date getQna_date() {
		return qna_date;
	}
	public void setQna_date(Date qna_date) {
		this.qna_date = qna_date;
	}
	public String getQna_secret() {
		return qna_secret;
	}
	public void setQna_secret(String qna_secret) {
		this.qna_secret = qna_secret;
	}
	public int getPro_code() {
		return pro_code;
	}
	public void setPro_code(int pro_code) {
		this.pro_code = pro_code;
	}
	
	public MultipartFile getQna_image() {
		return qna_image;
	}
	public void setQna_image(MultipartFile qna_image) {
		this.qna_image = qna_image;
	}
	
	@Override
	public String toString() {
		return "QnaVO [qna_code=" + qna_code + ", member_id=" + member_id + ", qna_pass=" + qna_pass + ", qna_subject="
				+ qna_subject + ", qna_content=" + qna_content + ", qna_file=" + qna_file + ", qna_real_file="
				+ qna_real_file + ", qna_re_ref=" + qna_re_ref + ", qna_re_lev=" + qna_re_lev + ", qna_re_seq="
				+ qna_re_seq + ", qna_date=" + qna_date + ", qna_secret=" + qna_secret + ", pro_code=" + pro_code
				+ ", qna_image=" + qna_image + "]";
	}
	
	
	
	
}

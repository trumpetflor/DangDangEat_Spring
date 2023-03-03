package com.thisteam.dangdangeat.vo;

/*
  create view wishlist_view
   as    select w.member_id, w.pro_code, w.ischecked, p.pro_name, p.pro_price, p.pro_real_thumb
      from wishlist w join product p 
      on (w.pro_code = p.pro_code);
 */
public class WishlistVO {
	
	private String member_id;
	private int pro_code;
	private String pro_name;
	private int pro_price;
	private String pro_real_thumb;
	private int cate_code;
	private String pro_brand;
	private int wish_count; // 찜 갯수
	
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
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
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
	public int getCate_code() {
		return cate_code;
	}
	public void setCate_code(int cate_code) {
		this.cate_code = cate_code;
	}
	public String getPro_brand() {
		return pro_brand;
	}
	public void setPro_brand(String pro_brand) {
		this.pro_brand = pro_brand;
	}
	public int getWish_count() {
		return wish_count;
	}
	public void setWish_count(int wish_count) {
		this.wish_count = wish_count;
	}
	@Override
	public String toString() {
		return "WishlistVO [member_id=" + member_id + ", pro_code=" + pro_code + ", pro_name=" + pro_name
				+ ", pro_price=" + pro_price + ", pro_real_thumb=" + pro_real_thumb + ", cate_code=" + cate_code
				+ ", pro_brand=" + pro_brand + ", wish_count=" + wish_count + "]";
	}
	
	
	
	
	
}















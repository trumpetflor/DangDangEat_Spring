package com.thisteam.dangdangeat.vo;

/*

create view member_mypage_view as 
select m.member_id 
	, (select count(*) from orders o where o.order_status = '1' and m.member_id = o.member_id ) order_count
	, (select count(*) from mc_view c where c.cp_status = 1 AND c.mc_stat = 1 AND c.mc_used = 'N' and m.member_id = c.member_id) coupon_count
	, (select count(*) from review r where m.member_id = r.member_id) review_count
	, (select count(*) from qna q where m.member_id = q.member_id) qna_count 
from member m;

Field       |Type       |Null|Key|Default|Extra|
------------+-----------+----+---+-------+-----+
member_id   |varchar(20)|YES |   |       |     |
order_count |bigint     |YES |   |       |     |
coupon_count|bigint     |YES |   |       |     |
review_count|bigint     |YES |   |       |     |
qna_count   |bigint     |YES |   |       |     |

 */
public class Member_Mypage_ViewVO {

	private String member_id;
	private int order_count;
	private int coupon_count;
	private int review_count;
	private int qna_count;
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getOrder_count() {
		return order_count;
	}
	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}
	public int getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(int coupon_count) {
		this.coupon_count = coupon_count;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public int getQna_count() {
		return qna_count;
	}
	public void setQna_count(int qna_count) {
		this.qna_count = qna_count;
	}
	
	@Override
	public String toString() {
		return "Member_Mypage_ViewVO [member_id=" + member_id + ", order_count=" + order_count + ", coupon_count="
				+ coupon_count + ", review_count=" + review_count + ", qna_count=" + qna_count + "]";
	}
	
}

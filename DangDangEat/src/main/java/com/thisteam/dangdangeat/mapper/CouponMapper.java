package com.thisteam.dangdangeat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.Coupon_viewVO;
import com.thisteam.dangdangeat.vo.Mc_viewVO;

public interface CouponMapper {
	
	
	//관리자페이지 쿠폰 리스트
	public List<Coupon_viewVO> selectCouponList();
	
	//쿠폰 전체개수 조회
	public int selectCouponCount();

	//사용가능한 쿠폰 조회
	public List<Map<String,Object>> selectMemberCoupon(@Param("id")String sId);
	
	//생일쿠폰 업데이트 작업
//	public void updateBirthCp(@Param("id") String sId);

	  
	public String selectCouponCode(String cp_code);

	//사용가능한 쿠폰인지 조회
	public String selectCouponCodebyUser(String cp_code);

	
	//쿠폰발행작업--------------
	//a. 회원이 이미 가지고 있는 쿠폰인지 조회
	public String selectIsPossessCode(@Param("id")String sId, @Param("cp_code") String cp_code);
	//b. 고객에게 쿠폰 발행 작업
	public int insertCouponCodeToMember(@Param("id") String sId, @Param("cp_code")String cp_code);

	//쿠폰 히스토리
	public List<Mc_viewVO> selectCouponHistory(@Param("id") String sId);

}

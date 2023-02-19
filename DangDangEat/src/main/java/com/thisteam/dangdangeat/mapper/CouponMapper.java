package com.thisteam.dangdangeat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.Coupon_viewVO;

public interface CouponMapper {
	
	
	public int insertCoupon(CouponVO coupon);
	
//	@Update()
	public int updateCoupon(CouponVO coupon);

//	@Delete()
	public int deleteCoupon(CouponVO coupon);

	   
	public int selectCouponCount();
	
	//특정 쿠폰 검색
	public Map selectCouponFromCouponView(String cp_code);

	//사용가능한 쿠폰 조회
	public List<Map<String,Object>> selectMemberCoupon(@Param("id")String sId);
	
//	public void updateBirthCp(@Param("id") String sId);

	  
	public String selectCouponCode(String cp_code);

	//쿠폰 검색 버튼
	public String selectCouponCodebyUser(String cp_code);

	public int InsertCouponCodeToMemCp(@Param("id") String sId, @Param("cp_code")String cp_code);
	
	//관리자페이지 쿠폰 리스트
	public List<Coupon_viewVO> selectCouponList();

}

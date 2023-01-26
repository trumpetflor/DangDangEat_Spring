package com.thisteam.dangdangeat.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.json.JSONArray;

import com.thisteam.dangdangeat.vo.CouponVO;

public interface CouponMapper {
	
	
	public int insertCoupon(CouponVO coupon);
	
//	@Update()
	public int updateCoupon(CouponVO coupon);

//	@Delete()
	public int deleteCoupon(CouponVO coupon);

	
	public int selectCouponCount();
	
	//TODO: JSON으로 변환해야함
	public HashMap selectCouponFromCouponView(String cp_code);
	

}

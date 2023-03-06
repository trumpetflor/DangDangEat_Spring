package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.CouponVO;
import com.thisteam.dangdangeat.vo.Coupon_viewVO;
import com.thisteam.dangdangeat.vo.MemberVO;

public interface AdminMapper {

	// 회원 목록 조회
	List<MemberVO> selectMemberList();

	// 회원 정보 수정
	int updateMember(MemberVO member);

	// 회원 삭제
	int deleteMember(MemberVO member);
	//=========================== hawon 시작=====================================
	//쿠폰 수정
	int updateCoupon(@Param("coupon") Coupon_viewVO coupon);
	
	//쿠폰 한개에 대한 정보 가져오기
	Coupon_viewVO selectCoupon(String cp_code);
	
	//쿠폰 등록
	int insertCoupon(@Param("coupon") CouponVO coupon);

	//존재하는 쿠폰 코드인지 확인
	String isExistCode(@Param("cp_code")String coupon_code);
	
	//쿠폰 삭제
	int deleteCoupon(String cp_code);

	//회원들에게 생일쿠폰 발급
	int insertBirthCouponToMc(@Param("cp_code") String cp_code);

	//회원가입시 쿠폰 등록
	int insertAutoCouponForNewMem(String member_id);
	
	
	//=========================== hawon 끝 =====================================
	

}














package com.thisteam.dangdangeat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thisteam.dangdangeat.vo.NoticeVO;

public interface BoardMapper {

	List<NoticeVO> getNoticeList(@Param("keyword") String keyword, 
								@Param("pageNum") int pageNum, 
								@Param("startRow") int startRow, 
								@Param("listLimit") int listLimit);

	int getNoticeListCount(@Param("keyword") String keyword);

	int insertNotice(NoticeVO notice);

	NoticeVO getDetailList(@Param("code") int notice_code);

	int modifyNotice(NoticeVO notice);

	int deleteNotice(@Param("code") int notice_code);
	
	

}














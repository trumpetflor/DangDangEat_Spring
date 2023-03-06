package com.thisteam.dangdangeat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thisteam.dangdangeat.mapper.BoardMapper;
import com.thisteam.dangdangeat.vo.NoticeVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;

	public List<NoticeVO> getNoticeList(String keyword, int pageNum, int startRow, int listLimit) {
		return mapper.getNoticeList(keyword,pageNum,startRow,listLimit);
	}

	public int getNoticeListCount(String keyword) {
		return mapper.getNoticeListCount(keyword);
	}

	public int insertNotice(NoticeVO notice) {
		return mapper.insertNotice(notice);
	}

	public NoticeVO getDetailList(int notice_code) {
		return mapper.getDetailList(notice_code);
	}

	public int modifyNotice(NoticeVO notice) {
		return mapper.modifyNotice(notice);
	}

	public int deleteNotice(int notice_code) {
		return mapper.deleteNotice(notice_code);
	}
	
	

}
















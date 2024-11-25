package com.twoX.agit.after.service;

import java.util.ArrayList;

import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.vo.PageInfo;

public interface AfterSchoolBoardService {

	//학생 방과후 글 갯수 조회
	int selectListCount(String stuId);

	//학생 방과후 글 리스트 조회
	ArrayList<AfterSchoolBoard> selectStudentBoardList(String stuId, PageInfo pi);

	//학생 방과후 상세 조회
	AfterSchoolBoard selectNowBoard(int boNo);

	//방과후 글 작성
	int insertAfterschoolBoard(AfterSchoolBoard asb);

	//방과후 글 수정
	int updateAfterschoolBoard(AfterSchoolBoard asb);

	//방과후 글 삭제
	int deleteAfterschoolBoard(AfterSchoolBoard asb);


}

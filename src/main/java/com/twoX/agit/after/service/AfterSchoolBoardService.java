package com.twoX.agit.after.service;

import java.util.ArrayList;

import java.util.List;

import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;

public interface AfterSchoolBoardService {
	//게시글 총 갯수 가져오기
		int afterSchoolListCount(String code);
		
		//게시글 목록 가져오기
		ArrayList<AfterSchoolBoard> afSelectList(String code, PageInfo pi);
		
		//Y
		ArrayList<Student> asCodeStudent(String code);
		
	
		
		//승인 필요 교직원 리스트
		ArrayList<Student> ajaxAcceptStudentListbyScCode(String scCode);

		//교직원 승인
		int RequestStudentByTcIdAndStatus(String stdId, String status, String scCode);
		
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

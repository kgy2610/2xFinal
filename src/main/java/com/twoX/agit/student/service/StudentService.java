package com.twoX.agit.student.service;

import java.util.ArrayList;

import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Homework;

public interface StudentService {
	// 학생 숙제 리스트 갯수 조회
	int selectListCount(String classCode, String stuId);

	// 학생 숙제 리스트 가져오기
	ArrayList<Homework> selectStudentHomeworkList(String classCode, String stuId, PageInfo pi);
		
	// 학생 숙제 번호에 맞는 게시물 조회
	Homework selectNowHomework(int boNo);
}

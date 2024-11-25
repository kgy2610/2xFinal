package com.twoX.agit.student.service;

import java.util.ArrayList;
import java.util.Map;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.student.model.vo.HomeworkSubmit;

public interface StudentService {
	// 학생 숙제 리스트 갯수 조회
	int selectListCount(String classCode, String stuId);


	// 학생 숙제 리스트 가져오기
	ArrayList<Homework> selectStudentHomeworkList(String classCode, String stuId, PageInfo pi);

	// 학생 숙제 번호에 맞는 게시물 조회
	Homework selectNowHomework(int boNo);

	// 게시글 번호로 HOMEWORK 정보를 가져오는 메서드
	Homework getHomeworkByBoNo(int boNo);

	// 학생이 숙제를 제출하는 메서드
	int submitHomework(HmSubmit hm);

//방과후
	int updateAfterschoolCode(Map<String, String> map);

	String afterschoolCode(String stuId);

	AfterSchoolStudent afterschoolStart(String stuId);

	AfterSchool afterschool(String code);

	String afterschoolTeacher(String tcId);
}

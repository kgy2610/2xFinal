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

	// 학생 숙제 업로드
	int insertStudentHomework(HomeworkSubmit hm);

	// 학생 숙제 답변 조회
	HomeworkSubmit selectNowAnswer(HomeworkSubmit hm);

	// 숙제 제출 상태 불러오기
	HomeworkSubmit selectHomeworkSubmit(int boNo, String stuId);
	
	// 숙제 수정하기
	int updateHomework(HomeworkSubmit hm);

	// 학생 숙제 점수 조회
	ArrayList<Double> selectStuScore(String stuId);
	
	// 학생 점수 평균 구하기
	ArrayList<Double> selectAVGstuScore(String classCode);
	
	//방과후
	int updateAfterschoolCode(Map<String, String> map);

	String afterschoolCode(String stuId);

	AfterSchoolStudent afterschoolStart(String stuId);

	AfterSchool afterschool(String code);

	String afterschoolTeacher(String tcId);

	int studentUpdateAfterschool(AfterSchoolStudent as);

	


}

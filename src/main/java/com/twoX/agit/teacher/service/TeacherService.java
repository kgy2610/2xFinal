package com.twoX.agit.teacher.service;

import java.util.ArrayList;
import java.util.List;

import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Teacher;

public interface TeacherService {
	
	// 회원정보 수정
	int updateInfo(Teacher t);

	// 반 삭제
	int classDelete(Teacher t);

	// 코드 중복 확인
	AfterSchool getAfterClassByTeacherId(String tcId);

	// 방과후 반 개설
	int makeAfterClass(AfterSchool as);
	
	// 숙제 전체조회
	int getListCount();

	// 숙제 페이지 조회
	ArrayList<Homework> getAllHomework(String tcId);

	// 숙제 등록
	int enrollHomework(String tcId, String classCode, String title, String subject, String content, String dueDate);

	// 숙제 조회
	Homework selectHomework(int bno);

	// 숙제 삭제
	int deleteHomework(String hmTitle);

	// 숙제 수정
	int updateHomework(String hmTitle, String subject, String deadLine, String hmContent);
	
	// 해당 숙제 페이지로 이동
	ArrayList<Homework> selectSubject(String classCode);
	
	// 숙제 상세 페이지 이동
	ArrayList<HmSubmit> gosubmitHomework(String title, String studentId);
	
	// 숙제 점수 및 말씀 부여
	int updateSubmitHomework(String teacherComment, int score, String stuId);
	
	// 출결
	ArrayList<Attendance> selectAttendance(String classCode);
	
	// 출결 정보 저장
	int insertAttendance(List<Attendance> attandanceList);
}
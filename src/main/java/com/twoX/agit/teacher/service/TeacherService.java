package com.twoX.agit.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
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
	
	// 출결 정보 저장
	int insertAttendance(List<Attendance> attandanceList);
	
	// 출결 상태 업데이트
	int updateAttendance(ArrayList<Attendance> updateAttendance);

	//code가져가서 확인
	List<Map<String, Object>> smCodeStudent(String code);

	
	//성적 과목 가져오기
	 List<Map<String, Object>> getStudentScoresByStuId(String stuId);
	
	  String udStudentStatus(String classCode, String stuId);
	  
	  ArrayList<Student> inStudentListbyScCode(String cCode);
	  
	  int inStudentListbyStatus( String status, String scCode, String stuId);
	  
}
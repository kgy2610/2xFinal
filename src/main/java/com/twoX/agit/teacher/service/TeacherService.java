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
	
	// 최근 숙제 번호 조회
	int getRecentHomeworkBoNo(String tcId); // 가장 최근 등록된 BO_NO 가져오기
	
	// 파일 추가
	int uploadHomeworkFile(int boNo, String originName, String changeName);

	// 숙제 조회
	Homework selectHomework(int bno);
	
	// 파일 삭제
	int deleteFile(int boNo, String changeName);

	// 숙제 삭제
	int deleteHomework(String hmTitle);

	// 숙제 수정
	int updateHomework(int boNo, String hmTitle, String subject, String deadLine, String hmContent);
	
	// 숙제 이동
	ArrayList<Homework> selectHomeworkList(int boNo);
	
	// 숙제 파일 수정
	int updateHomeworkFile(int boNo, String originName, String newFileName, String changeName);
	
	// 해당 숙제 페이지로 이동
	ArrayList<Homework> selectSubject(String classCode);
	
	// 숙제 상세 페이지 이동
	ArrayList<HmSubmit> gosubmitHomework(String title, String studentId);
	
	// 숙제 점수 및 말씀 부여
	int updateSubmitHomework(int boNo, String teacherComment, int score, String stuId);
	
	// 오늘날짜 출결 조회
	ArrayList<Attendance> selectAttendanceList(String aDate);
	
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
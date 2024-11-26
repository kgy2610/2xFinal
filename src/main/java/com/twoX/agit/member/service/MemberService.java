package com.twoX.agit.member.service;

import java.util.ArrayList;
import java.util.Map;

import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.teacher.model.vo.TeacherMemo;
import com.twoX.agit.teacher.model.vo.TeacherNotice;

public interface MemberService {
	
	// ------------------------------- 공용 -------------------------------
	// 아이디 찾기
	Parents findId(Parents p);

	// 비밀번호 찾기
	Parents findPwd(Parents p);

	int updatePwd(Parents p);
	
	// ------------------------------- 학생 -------------------------------
	//학생로그인
	Student loginStudent(Student s);

	//학생 회원가입
	int insertStudent(Student s);

	//아이디 중복찾기
	int idCheck(String checkId);
	
	// 반 참가
	int studentUpdateClassCode(Student s);

	// id 확인
	Student selectInfo(String id);

	// 학교 조회
	String getSchoolNameByClassCode(String classCode);

	// 선생님 조회
	String teacherName(String classCode);

	// 정보 수정
	int studentUpdate(Map<String, String> map);

	// 비밀번호 수정
	int studentPwdUpdate(Map<String, String> map);

	// 프로필 사진 변경
	int studentSelectImg(Map<String, String> map);

	// 급식
	String selectOeCode(String schoolCode);
	
	// ------------------------------- 부모님 -------------------------------
	// 부모님 로그인
	Parents loginParents(Parents p);
		
	// 부모님 회원가입
	int insertParents(Parents p);
	
	// 부모님 회원가입에서 학생 아이디 검색
	ArrayList<Student> selectStudentList(Student s);
	
	// 부모님 아이디로 학생찾기
	Student selectStudent(Parents p);
	
	//반코드로 선생님 찾기
	Teacher selectTeacher(Student s);
	
	// 부모님 정보수정
	int updateParentsInfo(Parents p);
	
	// ------------------------------- 선생님 -------------------------------
	
	// 선생님 로그인
	Teacher loginTeacher(Teacher t);
	
	//선생님 회원가입
	int insertTeacher(Teacher t);

	//선생님 회원가입에서 학교 코드 검색
	ArrayList<School> searchSchoolsByName(String scName);
	
	//선생님 비밀번호 수정
	int updatePassword(Teacher t);

	// 반 개설
	int updateClassCode(Teacher t);
	
	//공지사항 총 갯수
	int getNoticeCount();
	
	//공지사항 리스트
	ArrayList<TeacherNotice> getTeacherNotices(int NoticeCount, String tcId);
	
	// 공지사항 추가
	int insertNotice(String tcId, String classCode, String noticeTitle);

	// 공지사항 수정
	int updateNotice(String noticeTitle, int noticeNo);

	// 공지사항 삭제
	int deleteNotice(String noticeTitle);

	// 메모 리스트
	ArrayList<TeacherMemo> getTeacherMemo(String classCode);

	// 메모 추가
	int insertMemo(String tcId, String classCode, String memoContent);

	// 메모 삭제
	int deleteMemo(String memoContent);
	
	// 숙제 전체조회
	int getListCount();

	// 숙제 페이지 조회
	ArrayList<Homework> getAllHomework();

	// 숙제 등록
	int enrollHomework(String tcId, String classCode, String title, String subject, String content, String dueDate);

	// 숙제 조회
	Homework selectHomework(int bno);

	// 숙제 삭제
	int deleteHomework(String hmTitle);

	// 숙제 수정
	int updateHomework(String hmTitle, String subject, String deadLine, String hmContent);
}

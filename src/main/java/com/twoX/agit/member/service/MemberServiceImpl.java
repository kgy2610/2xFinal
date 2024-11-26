package com.twoX.agit.member.service;

import java.util.ArrayList;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.member.model.dao.MemberDao;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.teacher.model.vo.TeacherMemo;
import com.twoX.agit.teacher.model.vo.TeacherNotice;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao memberDao;

	// ------------------------------- 공용 -------------------------------
	// 아이디 중복 확인
	@Override
	public int idCheck(String checkId) {
		return memberDao.idCheck(sqlSession,checkId);
	}
	
	// 아이디 찾기
	public Parents findId(Parents p) {
		return memberDao.findId(sqlSession, p);
	}

	// 비밀번호 찾기
	public Parents findPwd(Parents p) {
		return memberDao.findPwd(sqlSession, p);
	}

	// 비밀번호 변경
	public int updatePwd(Parents p) {
		int result = 0;
		result += memberDao.updateStudentPwd(sqlSession, p);
		result += memberDao.updateParentsPwd(sqlSession, p);
		result += memberDao.updateTeacherPwd(sqlSession, p);
		return result;
	}
	
	// ------------------------------- 학생 -------------------------------
	// 학생 - 회원가입
	@Override
	public int insertStudent(Student s) {
		return memberDao.insertStudent(sqlSession,s);
	}
	
	// 학생 - 로그인
	@Override
	public Student loginStudent(Student s) {
		return memberDao.loginStudent(sqlSession, s);
	}
	
	// 학생 - 학교 조회
	@Override
	public String getSchoolNameByClassCode(String classCode) {
		return memberDao.getSchoolNameByClassCode(sqlSession,classCode);
	}
	
	// 학생 - 아이디 확인
	@Override
	public Student selectInfo(String id) {
		return memberDao.selectInfo(sqlSession, id);
	}
	
	// 학생 - 반 참가
	@Override
	public int studentUpdateClassCode(Student s) {
		return memberDao.studentUpdateClassCode(sqlSession,s);
	}
	
	// 학생 - 선생님 조회
	@Override
	public String teacherName(String classCode) {
		return memberDao.teacherName(sqlSession, classCode);
	}
	
	// 학생 - 정보 수정
	@Override
	public int studentUpdate(Map<String, String> map) {
		return memberDao.studentUpdate(sqlSession, map);
	}
	
	// 학생 - 비밀번호 수정
	@Override
	public int studentPwdUpdate(Map<String, String> map) {
		return memberDao.studentPwdUpdate(sqlSession, map);
	}
	
	// 학생 - 프로필 사진 변경
	@Override
	public int studentSelectImg(Map<String, String> map) {
		return memberDao.studentSelectImg(sqlSession,map);
	}
	
	// 학생 - 급식 조회
	@Override
	public String selectOeCode(String schoolCode) {
		return memberDao.selectOeCode(sqlSession,schoolCode);
	}
	
	// ------------------------------- 부모님 -------------------------------
	// 부모님 - 회원가입
	@Override
	public int insertParents(Parents p) {
		return memberDao.insertParents(sqlSession, p);
	}

	// 부모님 - 회원가입 (자녀 찾기)
	@Override
	public ArrayList<Student> selectStudentList(Student s) {
		return memberDao.selectStudentList(sqlSession, s);
	}

	// 부모님 - 로그인
	@Override
	public Parents loginParents(Parents p) {
		return memberDao.loginParents(sqlSession, p);
	}
	
	// 부모님 - 아이디로 학생 찾기
	@Override
	public Student selectStudent(Parents p) {
		return memberDao.selectStudent(sqlSession, p);
	}
	
	// 부모님 - 반 코드로 선생님 찾기
	@Override
	public Teacher selectTeacher(Student s) {
		return memberDao.selectTeacher(sqlSession, s);
	}
	
	// 부모님 - 정보 수정
	@Override
	public int updateParentsInfo(Parents p) {
		return memberDao.updateParentsInfo(sqlSession, p);
	}
	
	// ------------------------------- 선생님 -------------------------------
	// 선생님 - 회원가입
	@Override
	public int insertTeacher(Teacher t) {
		return memberDao.insertTeacher(sqlSession, t);
	}

	// 선생님 - 회원가입(학교 찾기)
	@Override
    public ArrayList<School> searchSchoolsByName(String scName) {
        return memberDao.selectList(sqlSession, scName);  // XML 매퍼의 id로 검색
    }
	
	// 선생님 - 로그인
	@Override
	public Teacher loginTeacher(Teacher t) {
		return memberDao.loginTeacher(sqlSession, t);
	}
	
	// 선생님 - 비밀번호 수정
	@Override
	public int updatePassword(Teacher t) {
		return memberDao.updatePassword(sqlSession, t);
	}

	// 선생님 - 반 개설
	@Override
	public int updateClassCode(Teacher t) {
		return memberDao.updateClassCode(sqlSession,t);
	}
	
	// 선생님 - 공지사항 총 갯수
	@Override
	public int getNoticeCount() {
		return memberDao.selectNoticeList(sqlSession);
	}
	
	//공지사항 
	@Override
	public ArrayList<TeacherNotice> getTeacherNotices(int NoticeCount, String tcId) {
		return memberDao.selectNoticeList(sqlSession, NoticeCount, tcId);
	}
	
	// 선생님 - 공지사항 추가
	public int insertNotice(String tcId, String classCode, String noticeTitle) {
		return memberDao.insertNotice(sqlSession, tcId, classCode, noticeTitle);
	}

	// 선생님 - 공지사항 수정
	@Override
	public int updateNotice(String noticeTitle, int noticeNo) {
		return memberDao.updateNotice(sqlSession, noticeTitle, noticeNo);
	}

	// 선생님 - 공지사항 삭제
	@Override
	public int deleteNotice(String noticeTitle) {
		return memberDao.deleteNotice(sqlSession, noticeTitle);
	}
	
	// 선생님 - 메모 전체조회
	@Override
	public ArrayList<TeacherMemo> getTeacherMemo(String classCode) {
		return memberDao.selectMemoList(sqlSession, classCode);
	}
	
	// 선생님 - 메모 추가
	@Override
	public int insertMemo(String tcId, String classCode, String memoContent) {
		return memberDao.insertMemo(sqlSession, tcId, classCode, memoContent);
	}

	// 선생님 - 메모 삭제
	@Override
	public int deleteMemo(String memoContent) {
		return memberDao.deleteMemo(sqlSession, memoContent);
	}
	
	// 선생님 - 숙제 페이징
	@Override
	public int getListCount() {
		return memberDao.selectListCount(sqlSession);
	}
	
	// 선생님 - 숙제 전체조회
	@Override
	public ArrayList<Homework> getAllHomework() {
		return memberDao.selectHomeworkList(sqlSession);
	}

	// 선생님 - 숙제 등록
	@Override
	public int enrollHomework(String tcId, String classCode, String title, String subject, String content,
			String dueDate) {
		return memberDao.enrollHomework(sqlSession, tcId, classCode, title, subject, content, dueDate);
	}

	// 선생님 - 숙제 조회
	@Override
	public Homework selectHomework(int bno) {
		return memberDao.selectHomework(sqlSession, bno);
	}

	// 선생님 - 숙제 삭제
	@Override
	public int deleteHomework(String hmTitle) {
		return memberDao.deleteHomework(sqlSession, hmTitle);
	}

	// 선생님 - 숙제 수정
	@Override
	public int updateHomework(String hmTitle, String subject, String deadLine, String hmContent) {
		return memberDao.updateHomework(sqlSession, hmTitle, subject, deadLine, hmContent);
	}
}

package com.twoX.agit.member.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.teacher.model.vo.TeacherMemo;
import com.twoX.agit.teacher.model.vo.TeacherNotice;

@Repository
public class MemberDao {
	
	// ------------------------------- 공용 -------------------------------
	// 아이디 찾기
	public Parents findId(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.selectOne("memberMapper.findId", p);
	}

	// 비밀번호 찾기
	public Parents findPwd(SqlSessionTemplate sqlSession, Parents p) {
		System.out.println(p);
		System.out.println("memberMapper.findPwd");
		return sqlSession.selectOne("memberMapper.findPwd", p);
	}

	// 학생, 부모님, 선생님 - 비밀번호 변경
	public int updateStudentPwd(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.update("memberMapper.updateStudentPwd", p);
	}

	public int updateParentsPwd(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.update("memberMapper.updateParentsPwd", p);
	}

	public int updateTeacherPwd(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.update("memberMapper.updateTeacherPwd", p);
	}
	
	// 아이디 중복 체크
	public int idCheck(SqlSessionTemplate sqlSession, String checkId) {
		return sqlSession.selectOne("memberMapper.idCheck", checkId);
	}

	// ------------------------------- 학생 -------------------------------
	//학생 - 회원가입
	public int insertStudent(SqlSessionTemplate sqlSession, Student s) {
		return sqlSession.insert("memberMapper.insertStudent",s);
	}
	
	//학생 - 로그인
	public Student loginStudent(SqlSessionTemplate sqlSession, Student s) {
		return sqlSession.selectOne("memberMapper.loginStudent", s);
	}
	
	// 학생 - 반 참가
	public int studentUpdateClassCode(SqlSessionTemplate sqlSession, Student s) {
		return sqlSession.update("memberMapper.studentUpdateClassCode", s);
	}

	// 학생 - id 확인
	public Student selectInfo(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("memberMapper.selectInfo", id);
	}

	// 학생 - 학교 조회
	public String getSchoolNameByClassCode(SqlSessionTemplate sqlSession, String classCode) {
		return sqlSession.selectOne("memberMapper.selectSchoolNameByClassCode", classCode);
	}

	// 학생 - 선생님이름조회
	public String teacherName(SqlSessionTemplate sqlSession, String classCode) {
		return sqlSession.selectOne("memberMapper.teacherName", classCode);
	}

	// 학생 - 정보수정
	public int studentUpdate(SqlSessionTemplate sqlSession, Map<String, String> map) {
		return sqlSession.update("memberMapper.studentUpdate", map);
	}

	// 학생 - 비번수정
	public int studentPwdUpdate(SqlSessionTemplate sqlSession, Map<String, String> map) {
		return sqlSession.update("memberMapper.studentPwdUpdate", map);
	}

	// 학생 - 프로필 변경
	public int studentSelectImg(SqlSessionTemplate sqlSession, Map<String, String> map) {
		return sqlSession.update("memberMapper.studentSelectImg", map);
	}

	// 학생 - 급식 조회
	public String selectOeCode(SqlSessionTemplate sqlSession, String schoolCode) {
		return sqlSession.selectOne("schoolMapper.selectOeCode", schoolCode);
	}
	
	// ------------------------------- 부모님 -------------------------------
	//부모님 - 로그인
	public Parents loginParents(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.selectOne("memberMapper.loginParents",p);
	}
	
	// 부모님 - 회원가입
	public int insertParents(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.insert("memberMapper.insertParents", p);
	}
		
	// 부모님 - 회원가입에서 자녀 아이디 찾기
	public ArrayList<Student> selectStudentList(SqlSessionTemplate sqlSession, Student s){
		return (ArrayList)sqlSession.selectList("memberMapper.selectStudentList",s);
	}
	
	// 부모님 - 아이디로 학생찾기
	public Student selectStudent(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.selectOne("memberMapper.selectStudent", p);
	}
	
	// 부모님 - 반코드로 선생님 찾기
	public Teacher selectTeacher(SqlSessionTemplate sqlSession, Student s) {
		return sqlSession.selectOne("memberMapper.selectTeacher", s);
	}

	// 부모님 - 정보수정
	public int updateParentsInfo(SqlSessionTemplate sqlSession, Parents p) {
		return sqlSession.insert("memberMapper.updateParentsInfo", p);
	}
	
	// ------------------------------- 선생님 -------------------------------
	// 선생님 - 로그인
	public Teacher loginTeacher(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.selectOne("memberMapper.loginTeacher", t);
	}
	
	// 선생님 - 회원가입
	public int insertTeacher(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.insert("memberMapper.insertTeacher", t);
	}
	
	// 선생님 - 회원가입에서 학교코드 찾기
	public ArrayList<School> selectList(SqlSessionTemplate sqlSession, String scName) {
		return (ArrayList)sqlSession.selectList("schoolMapper.searchSchoolsByName", scName);
	}
	
	// 선생님 비밀번호 수정
	public int updatePassword(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.update("memberMapper.updatePassword", t);
	}
	
	// 선생님 - 반 개설
	public int updateClassCode(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.update("memberMapper.updateClassCode", t);
	}
	
	// 선생님 - 공지사항 조회
	public ArrayList<TeacherNotice> selectNoticeList(SqlSessionTemplate sqlSession, String classCode) {
		return (ArrayList) sqlSession.selectList("noticeMapper.selectNotice", classCode);
	}
	
	// 선생님 - 공지사항 추가
	public int insertNotice(SqlSessionTemplate sqlSession, String tcId, String classCode, String noticeTitle) {
		// 맵에 값 담기
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tcId", tcId);
		params.put("classCode", classCode);
		params.put("noticeTitle", noticeTitle); // noticeTitle이 null일 경우 여기에 null이 들어간다.

		// MyBatis 쿼리 실행
		return sqlSession.insert("noticeMapper.insertNotice", params);
	}

	// 선생님 - 공지사항 수정
	public int updateNotice(SqlSessionTemplate sqlSession, String noticeTitle) {
		return sqlSession.update("noticeMapper.updateNotice", noticeTitle);
	}

	// 선생님 - 공지사항 삭제
	public int deleteNotice(SqlSessionTemplate sqlSession, String noticeTitle) {
		return sqlSession.delete("noticeMapper.deleteNotice", noticeTitle);
	}
	
	// 선생님 - 메모 조회
	public ArrayList<TeacherMemo> selectMemoList(SqlSessionTemplate sqlSession, String classCode) {
		return (ArrayList) sqlSession.selectList("memoMapper.selectMemo", classCode);
	}
	
	// 선생님 - 메모 추가
	public int insertMemo(SqlSessionTemplate sqlSession, String tcId, String classCode, String memoContent) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tcId", tcId);
		params.put("classCode", classCode);
		params.put("mmContent", memoContent);

		return sqlSession.insert("memoMapper.insertMemo", params);
	}

	// 선생님 - 메모 삭제
	public int deleteMemo(SqlSessionTemplate sqlSession, String memoContent) {
		return sqlSession.delete("memoMapper.deleteMemo", memoContent);
	}

	// 선생님 - 숙제 조회
	public int selectListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("homeworkMapper.selectListCount");
	}

	// 선생님 - 숙제 전체 조회
	public ArrayList<Homework> selectHomeworkList(SqlSessionTemplate sqlSession) {
		return (ArrayList) sqlSession.selectList("homeworkMapper.selectAllList");
	}

	// 선생님 - 숙제 등록
	public int enrollHomework(SqlSessionTemplate sqlSession, String tcId, String classCode, String title, String subject, String content, String dueDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tcId", tcId);
		params.put("classCode", classCode);
		params.put("hmTitle", title);
		params.put("subject", subject);
		params.put("hmContent", content);
		params.put("deadLine", dueDate);

		return sqlSession.insert("homeworkMapper.insertHomework", params);
	}

	// 선생님 - 숙제 조회
	public Homework selectHomework(SqlSessionTemplate sqlSession, int bno) {
		return sqlSession.selectOne("homeworkMapper.selectHomework", bno);
	}

	// 선생님 - 숙제 삭제
	public int deleteHomework(SqlSessionTemplate sqlSession, String hmTitle) {
		return sqlSession.delete("homeworkMapper.deleteHomework", hmTitle);
	}

	// 선생님 - 숙제 수정
	public int updateHomework(SqlSessionTemplate sqlSession, String hmTitle, String subject, String deadLine, String hmContent) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hmTitle", hmTitle);
		params.put("subject", subject);
		params.put("deadLine", deadLine);
		params.put("hmContent", hmContent);

		return sqlSession.update("homeworkMapper.updateHomework", params);
	}
	
	// 관리자 - 총 교직원 수
	public int getTeacherCount(SqlSessionTemplate sqlSession) {
	    return sqlSession.selectOne("memberMapper.getTeacherCount"); // sqlSession을 직접 사용
	}
	
	// 관리자 - 총 학생 수
	public int getStudentCount(SqlSessionTemplate sqlSession, String scCode) {
		return sqlSession.selectOne("memberMapper.getStudentCountBySchoolCode", scCode);
	}
}

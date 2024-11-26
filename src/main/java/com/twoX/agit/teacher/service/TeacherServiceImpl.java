package com.twoX.agit.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.teacher.model.dao.TeacherDao;

@Service
public class TeacherServiceImpl  implements TeacherService{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public int updateInfo(Teacher t) {
		return teacherDao.updateInfo(sqlSession, t);
	}
	
	@Override
	public int classDelete(Teacher t) {
		return teacherDao.classDelete(sqlSession,t);
	}


	@Override
	public AfterSchool getAfterClassByTeacherId(String tcId) {
		return teacherDao.getAfterClassByTeacherId(sqlSession, tcId);
	}
	
	@Override
	public int makeAfterClass(AfterSchool as) {
		return teacherDao.makeAfterClass(sqlSession, as);
	}
	
	// 숙제 페이징
	@Override
	public int getListCount() {
		return teacherDao.selectListCount(sqlSession);
	}

	// 숙제 전체조회
	@Override
	public ArrayList<Homework> getAllHomework(String tcId) {
		return teacherDao.selectHomeworkList(sqlSession, tcId);
	}

	// 숙제 등록
	@Override
	public int enrollHomework(String tcId, String classCode, String title, String subject, String content,
			String dueDate) {
		return teacherDao.enrollHomework(sqlSession, tcId, classCode, title, subject, content, dueDate);
	}

	// 숙제 조회
	@Override
	public Homework selectHomework(int bno) {
		return teacherDao.selectHomework(sqlSession, bno);
	}

	// 숙제 삭제
	@Override
	public int deleteHomework(String hmTitle) {
		return teacherDao.deleteHomework(sqlSession, hmTitle);
	}

	// 숙제 수정
	@Override
	public int updateHomework(String hmTitle, String subject, String deadLine, String hmContent) {
		return teacherDao.updateHomework(sqlSession, hmTitle, subject, deadLine, hmContent);
	}

	// 해당 숙제 페이지로 이동
	@Override
	public ArrayList<Homework> selectSubject( String classCode) {
		return (ArrayList)teacherDao.selectSubject(sqlSession,classCode);
	}
	
	// 숙제 상세 페이지
	@Override
	public ArrayList<HmSubmit> gosubmitHomework(String title, String studentId) {
		return (ArrayList)teacherDao.selectsubmitHomework(sqlSession, title, studentId);
	}
	
	// 숙제 점수 및 말씀 부여
	@Override
	public int updateSubmitHomework(String teacherComment, int score, String stuId) {
		return teacherDao.updateSubmitHomework(sqlSession, teacherComment, score, stuId);
	}

	// 출결 
	@Override
	public ArrayList<Attendance> selectAttendance(String classCode) {
		return teacherDao.selectAttendance(sqlSession, classCode);
	}

	// 출결 저장
	@Override
	public int insertAttendance(List<Attendance> attandanceList) {
		return teacherDao.insertAttendance(sqlSession, attandanceList);
	}

	@Override
	public List<Map<String, Object>> smCodeStudent(String code) {
		return teacherDao.smCodeStudent(sqlSession, code);
	}

}
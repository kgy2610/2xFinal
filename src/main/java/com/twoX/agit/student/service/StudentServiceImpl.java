package com.twoX.agit.student.service;

import java.util.ArrayList;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.student.model.dao.StudentDao;
import com.twoX.agit.student.model.vo.HomeworkSubmit;



@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private StudentDao studentDao;
	
	// 학생 숙제 리스트 갯수 조회
	@Override
	public int selectListCount(String classCode, String stuId) {
		return studentDao.selectListCount(sqlSession, classCode, stuId);
	}
	
	//방과후
	@Override
	public int updateAfterschoolCode(Map<String, String> map) {
		return studentDao.updateAfterschoolCode(sqlSession, map);
	}

	@Override
	public String afterschoolCode(String stuId) {
		return studentDao.afterschoolCode(sqlSession, stuId);
	}

	@Override
	public AfterSchoolStudent afterschoolStart(String stuId) {
		return studentDao.afterschoolStart(sqlSession, stuId);
	}

	@Override
	public AfterSchool afterschool(String code) {
		return studentDao.afterschool(sqlSession, code);
	}

	@Override
	public String afterschoolTeacher(String tcId) {
		return studentDao.afterschoolTeacher(sqlSession, tcId);
	}


	// 학생 숙제 리스트 가져오기
	@Override
	public ArrayList<Homework> selectStudentHomeworkList(String classCode, String stuId, PageInfo pi) {
		return studentDao.selectStudentHomeworkList(sqlSession, classCode, stuId, pi);
	}

	// 학생 숙제 번호에 맞는 게시물 조회
	@Override
	public Homework selectNowHomework(int boNo) {
		return studentDao.selectNowHomework(sqlSession, boNo);
	}
	
	// 학생 숙제 업로드
	@Override
	public int insertStudentHomework(HomeworkSubmit hm) {
		return studentDao.insertStudentHomework(sqlSession, hm);
	}

	// 학생 숙제 답변 조회
	@Override
	public HomeworkSubmit selectNowAnswer(int boNo) {
		return studentDao.selectNowAnswer(sqlSession, boNo);
	}

	// 숙제 제출 여부 가져오기
	@Override
	public HomeworkSubmit selectHomeworkSubmit(int boNo, String stuId) {
		return studentDao.selectHomeworkSubmit(sqlSession, boNo, stuId);
	}

}

package com.twoX.agit.student.service;

import java.util.ArrayList;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.student.model.dao.StudentDao;
import com.twoX.agit.student.model.vo.HomeworkSubmit;

@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private StudentDao studentDao;
	
	@Override
	public ArrayList<HomeworkSubmit> showHomeworkList(String classCode, String stuId) {
		return studentDao.showHomeworkList(sqlSession, classCode, stuId);
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


}

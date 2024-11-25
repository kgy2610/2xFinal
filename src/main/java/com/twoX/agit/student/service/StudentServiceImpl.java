package com.twoX.agit.student.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

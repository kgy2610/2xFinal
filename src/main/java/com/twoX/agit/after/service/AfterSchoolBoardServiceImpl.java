package com.twoX.agit.after.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.dao.AfterSchoolBoardDao;
import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Student;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AfterSchoolBoardServiceImpl implements AfterSchoolBoardService {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private AfterSchoolBoardDao afterSchoolBoardDao;

	@Override
	public int afterSchoolListCount(String code) {
		return afterSchoolBoardDao.afterSchoolListCount(sqlSession, code);
	}

	@Override
	public ArrayList<AfterSchoolBoard> afSelectList(String code, PageInfo pi) {

		return afterSchoolBoardDao.afSelectList(sqlSession, code, pi);
	}

	@Override
	public ArrayList<Student> asCodeStudent(String code) {
		return afterSchoolBoardDao.asCodeStudent(sqlSession, code);
	}
	


	@Override
	public ArrayList<Student> ajaxAcceptStudentListbyScCode(String scCode) {
		return afterSchoolBoardDao.activeList(sqlSession, "sqlSession", scCode);
	}

	@Override
	public int RequestStudentByTcIdAndStatus(String stdId, String status, String scCode) {
		return afterSchoolBoardDao.RequestTeacherByTcIdAndStatus(sqlSession, stdId, status, scCode);
	}


	

	

}

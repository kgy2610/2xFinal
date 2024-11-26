package com.twoX.agit.after.service;

import java.util.ArrayList;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.dao.AfterSchoolBoardDao;
import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.vo.PageInfo;
<<<<<<< HEAD
=======
import com.twoX.agit.member.model.vo.Student;
>>>>>>> e3d0cc382d5d5780c014b4e5e86c845b30af5f4d

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AfterSchoolBoardServiceImpl implements AfterSchoolBoardService {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private AfterSchoolBoardDao afterSchoolBoardDao;

	@Override
<<<<<<< HEAD
	public int selectListCount(String stuId) {
		return afterSchoolBoardDao.selectListCount(sqlSession,stuId);
	}

	@Override
	public ArrayList<AfterSchoolBoard> selectStudentBoardList(String stuId, PageInfo pi) {
		return afterSchoolBoardDao.selectStudentBoardList(sqlSession,stuId,pi);
	}

	@Override
	public AfterSchoolBoard selectNowBoard(int boNo) {
		return afterSchoolBoardDao.selectNowBoard(sqlSession,boNo);
	}

	@Override
	public int insertAfterschoolBoard(AfterSchoolBoard asb) {
		return afterSchoolBoardDao.insertAfterschoolBoard(sqlSession,asb);
	}

	@Override
	public int updateAfterschoolBoard(AfterSchoolBoard asb) {
		return afterSchoolBoardDao.updateAfterschoolBoard(sqlSession,asb);
	}

	@Override
	public int deleteAfterschoolBoard(AfterSchoolBoard asb) {
		return afterSchoolBoardDao.deleteAfterschoolBoard(sqlSession,asb);
=======
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
>>>>>>> e3d0cc382d5d5780c014b4e5e86c845b30af5f4d
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

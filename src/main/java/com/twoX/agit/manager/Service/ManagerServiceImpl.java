package com.twoX.agit.manager.Service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.manager.model.dao.ManagerDao;
import com.twoX.agit.member.model.dao.MemberDao;
import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Teacher;

@Service
public class ManagerServiceImpl implements ManagerService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private MemberDao memberDao;

	//관리자 메인 페이지 학교 이름 불러오기
	@Override
	public School getSchoolNameByScCode(String scCode) {
		return managerDao.getSchoolName(sqlSession, scCode);
	}

	//관리자 메인페이지 총 교직원 수 불러오기
	@Override
	public int getTeacherCount() {
		return memberDao.getTeacherCount(sqlSession); // SqlSessionTemplate을 넘김
	}

	//관리자 메인페이지 총 학생 수 불러오기
	@Override
	public int getStudentCountBySchoolCode(String scCode) {
		return memberDao.getStudentCount(sqlSession, scCode);
	}

	//관리자 서브페이지 승인 완료 교직원 리스트 
	@Override
	public ArrayList<Teacher> getActiveTeachersByScCode(String scCode) {
		return managerDao.selectList(sqlSession, "sqlSession", scCode);
	}

	//관리자 서브페이지 승인 필요 교직원 리스트
	@Override
	public ArrayList<Teacher> ajaxAcceptTeacherListbyScCode(String scCode) {
		return managerDao.activeList(sqlSession, "sqlSession", scCode);
	}

	//관리자 서브페이지 교직원 학교 코드 삭제
	@Override
	public int deleteTeacherByTcIdAndScCode(String tcId, String scCode) {
		return managerDao.deleteTeacherByTcIdAndScCode(sqlSession, tcId, scCode);
	}

	//관리자 서브페이지 교직원 승인
	@Override
	public int RequestTeacherByTcIdAndStatus(String tcId, String status, String scCode) {
		return managerDao.RequestTeacherByTcIdAndStatus(sqlSession, tcId, status, scCode);
	}
}

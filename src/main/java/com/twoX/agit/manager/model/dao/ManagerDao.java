package com.twoX.agit.manager.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Teacher;

@Repository
public class ManagerDao {

	public School getSchoolName(SqlSession sqlSession, String scCode) {
		return sqlSession.selectOne("schoolMapper.getSchoolNamebyScCode", scCode);
	}

	public ArrayList<Teacher> selectList(SqlSession sqlSession, String string, String scCode) {
		return (ArrayList)sqlSession.selectList("memberMapper.getActiveTeachersByScCode", scCode);
	}

	public ArrayList<Teacher> activeList(SqlSessionTemplate sqlSession, String string, String scCode) {
		return (ArrayList)sqlSession.selectList("memberMapper.acceptTeacherListbyScCode", scCode);
	}

	public int deleteTeacherByTcIdAndScCode(SqlSessionTemplate sqlSession, String tcId, String scCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tcId", tcId);
		params.put("scCode", scCode);
		
		return sqlSession.update("memberMapper.deleteTeacherByTcIdAndScCode", params);
	}

	public int RequestTeacherByTcIdAndStatus(SqlSessionTemplate sqlSession, String tcId, String status,String scCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tcId",tcId);
		params.put("status", status);
		params.put("scCode", scCode);
		return sqlSession.update("memberMapper.RequestTeacherByTcIdAndStatus", params);
	}
}
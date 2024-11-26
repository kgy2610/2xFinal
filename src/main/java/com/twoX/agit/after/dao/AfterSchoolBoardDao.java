package com.twoX.agit.after.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Student;



@Repository
public class AfterSchoolBoardDao {
	
	  @Autowired
	    private SqlSession sqlSession; 
	
	public int afterSchoolListCount(SqlSessionTemplate sqlSession, String code) {
		int count = sqlSession.selectOne("afterMapper.afterBoardCount", code);
		System.out.println(count);
		return count;
	}
	
	public ArrayList<AfterSchoolBoard> afSelectList(SqlSessionTemplate sqlSession,String code, PageInfo pi){
		
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("afterMapper.selectAfterBoard", code, rowBounds);
	}
	
	
	public ArrayList<Student> asCodeStudent(SqlSessionTemplate sqlSession, String code){	
		return (ArrayList)sqlSession.selectList("afterMapper.asCodeStudent", code);
	}
	
	
	public ArrayList<Student> activeList(SqlSessionTemplate sqlSession, String status, String scCode) {
		return (ArrayList)sqlSession.selectList("afterMapper.acceptTeacherListbyScCode", scCode);
	}

	public int selectListCount(SqlSessionTemplate sqlSession, String stuId) {
		return sqlSession.selectOne("afterMapper.selectListcount",stuId);
	}

	public ArrayList<AfterSchoolBoard> selectStudentBoardList(SqlSessionTemplate sqlSession, String stuId,
			PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("afterMapper.selectStudentBoardList", stuId,rowBounds);
	}

	public AfterSchoolBoard selectNowBoard(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.selectOne("afterMapper.selectNowBoard",boNo);
	}

	public int insertAfterschoolBoard(SqlSessionTemplate sqlSession, AfterSchoolBoard asb) {
		return sqlSession.insert("afterMapper.insertStudentBoard",asb);
	}

	public int updateAfterschoolBoard(SqlSessionTemplate sqlSession, AfterSchoolBoard asb) {
		return sqlSession.update("afterMapper.updateStudentBoard",asb);
	}

	public int deleteAfterschoolBoard(SqlSessionTemplate sqlSession, AfterSchoolBoard asb) {
		return sqlSession.delete("afterMapper.deleteStudentBoard",asb);
	}


	public int RequestTeacherByTcIdAndStatus(SqlSessionTemplate sqlSession, String stuId, String status,String scCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stuId",stuId);
		params.put("status", status);
		params.put("scCode", scCode);
		return sqlSession.update("afterMapper.RequestTeacherByTcIdAndStatus", params);
	}

}

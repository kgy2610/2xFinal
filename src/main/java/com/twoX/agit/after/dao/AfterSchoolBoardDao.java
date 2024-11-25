package com.twoX.agit.after.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.common.vo.PageInfo;

@Repository
public class AfterSchoolBoardDao {

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

}

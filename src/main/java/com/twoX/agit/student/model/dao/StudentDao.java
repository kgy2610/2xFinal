package com.twoX.agit.student.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Homework;

@Repository
public class StudentDao {
	// 학생 숙제 리스트 갯수 조회
	public int selectListCount(SqlSessionTemplate sqlSession, String classCode, String stuId) {
		HashMap<String, String> params = new HashMap<>();
		params.put("classCode", classCode);
		params.put("stuId", stuId);

		return sqlSession.selectOne("studentMapper.selectListCount", params);
	}

	// 학생 숙제 리스트 가져오기
	public ArrayList<Homework> selectStudentHomeworkList(SqlSessionTemplate sqlSession, String classCode, String stuId, PageInfo pi) {
		HashMap<String, String> params = new HashMap<>();
		params.put("classCode", classCode);
		params.put("stuId", stuId);

		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();

		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

		return (ArrayList) sqlSession.selectList("studentMapper.selectStudentHomeworkList", params, rowBounds);
	}

	// 학생 숙제 번호에 맞는 게시글 보기
	public Homework selectNowHomework(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.selectOne("studentMapper.selectNowHomework", boNo);
	}
}

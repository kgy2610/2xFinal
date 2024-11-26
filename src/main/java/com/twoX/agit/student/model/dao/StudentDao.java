package com.twoX.agit.student.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
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

	// 게시글 번호로 숙제 조회
	public Homework selectHomeworkByBoNo(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.selectOne("studentMapper.selectHomeworkByBoNo", boNo);
	}

	// 숙제 제출
	public int insertHomeworkSubmission(SqlSessionTemplate sqlSession, HmSubmit hm) {
		return sqlSession.selectOne("studentMapper.insertHomeworkSubmission", hm);
	}

	// 방과후
			public int updateAfterschoolCode(SqlSessionTemplate sqlSession, Map<String, String> map) {
				return sqlSession.insert("afterschoolmapper.insertAfterschoolCode", map);
			}

			public String afterschoolCode(SqlSessionTemplate sqlSession, String stuId) {
				return sqlSession.selectOne("afterschoolmapper.afterschoolCode", stuId);
			}

			public AfterSchoolStudent afterschoolStart(SqlSessionTemplate sqlSession, String stuId) {
				return sqlSession.selectOne("afterschoolmapper.afterschoolStart", stuId);
			}

			public AfterSchool afterschool(SqlSessionTemplate sqlSession, String code) {
				return sqlSession.selectOne("afterschoolmapper.afterschool", code);
			}

			public String afterschoolTeacher(SqlSessionTemplate sqlSession, String tcId) {
				return sqlSession.selectOne("afterschoolmapper.afterschoolTeacher", tcId);
			}
}

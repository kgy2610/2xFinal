package com.twoX.agit.teacher.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Teacher;

@Repository
public class TeacherDao {
	// 선생님 정보 수정
	public int updateInfo(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.update("memberMapper.updateInfo", t);
	}

	// 반 삭제
	public int classDelete(SqlSessionTemplate sqlSession, Teacher t) {
		return sqlSession.update("memberMapper.classDelete", t);
	}

	// 방과후 개설 코드 중복 확인
	public AfterSchool getAfterClassByTeacherId(SqlSessionTemplate sqlSession, String tcId) {
		return sqlSession.selectOne("memberMapper.getAfterClassByTeacherId", tcId);
	}

	// 방과후 반 개설
	public int makeAfterClass(SqlSessionTemplate sqlSession, AfterSchool as) {
		return sqlSession.insert("memberMapper.updateAfterClass", as);
	}
	
	// 숙제 조회
	public int selectListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("homeworkMapper.selectListCount");
	}

	// 숙제 전체 조회
	public ArrayList<Homework> selectHomeworkList(SqlSessionTemplate sqlSession) {
		return (ArrayList) sqlSession.selectList("homeworkMapper.selectAllList");
	}

	// 숙제 등록
	public int enrollHomework(SqlSessionTemplate sqlSession, String tcId, String classCode, String title,
			String subject, String content, String dueDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tcId", tcId);
		params.put("classCode", classCode);
		params.put("hmTitle", title);
		params.put("subject", subject);
		params.put("hmContent", content);
		params.put("deadLine", dueDate);

		return sqlSession.insert("homeworkMapper.insertHomework", params);
	}

	// 숙제 조회
	public Homework selectHomework(SqlSessionTemplate sqlSession, int bno) {
		return sqlSession.selectOne("homeworkMapper.selectHomework", bno);
	}

	// 숙제 삭제
	public int deleteHomework(SqlSessionTemplate sqlSession, String hmTitle) {
		return sqlSession.delete("homeworkMapper.deleteHomework", hmTitle);
	}

	// 숙제 수정
	public int updateHomework(SqlSessionTemplate sqlSession, String hmTitle, String subject, String deadLine,
			String hmContent) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hmTitle", hmTitle);
		params.put("subject", subject);
		params.put("deadLine", deadLine);
		params.put("hmContent", hmContent);

		return sqlSession.update("homeworkMapper.updateHomework", params);
	}
}
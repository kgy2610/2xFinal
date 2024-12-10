package com.twoX.agit.student.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.student.model.vo.HomeworkSubmit;


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

	// 학생 숙제 제출
	public int insertStudentHomework(SqlSessionTemplate sqlSession, HomeworkSubmit hm) {
		return sqlSession.insert("studentMapper.insertStudentHomework", hm);
	}

	// 학생 숙제 답변 조회
	public HomeworkSubmit selectNowAnswer(SqlSessionTemplate sqlSession, HomeworkSubmit hm) {
	    HomeworkSubmit result = sqlSession.selectOne("studentMapper.selectNowAnswer", hm);

	    // 디버깅: 반환된 HomeworkSubmit 객체의 originName과 changeName 값 출력
	    if (result != null) {
	        System.out.println("Origin Name: " + result.getOriginName());
	        System.out.println("Change Name: " + result.getChangeName());
	    } else {
	        System.out.println("Result is null for BO_NO: " + hm.getBoNo());
	    }

	    return result;
	}

	// 학생 숙제 제출 상태 불러오기
	public HomeworkSubmit selectHomeworkSubmit(SqlSessionTemplate sqlSession, int boNo, String stuId) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("boNo", boNo);
		params.put("stuId", stuId);
		
		return sqlSession.selectOne("studentMapper.selectHomeworkSubmit", params);
	}
	// 숙제 수정하기
	public int updateStuHomework(SqlSessionTemplate sqlSession, HomeworkSubmit hm) {
		return sqlSession.update("studentMapper.updateStuHomework", hm);
	}
	
	// 숙제 점수 불러오기
	public ArrayList<Double> selectStuScore(SqlSessionTemplate sqlSession, String stuId) {
		return (ArrayList)sqlSession.selectList("studentMapper.selectStuScore", stuId);
	}

	// 숙제 점수 평균 불러오기
	public ArrayList<Double> selectAVGstuScore(SqlSessionTemplate sqlSession, String classCode) {
		return (ArrayList)sqlSession.selectList("studentMapper.selectAVGstuScore", classCode);
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

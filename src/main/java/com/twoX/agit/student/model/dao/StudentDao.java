package com.twoX.agit.student.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.student.model.vo.HomeworkSubmit;

@Repository
public class StudentDao {
	//숙제 리스트
	public ArrayList<HomeworkSubmit> showHomeworkList(SqlSessionTemplate sqlSession, String classCode, String stuId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ClassCode", classCode);
		paramMap.put("stuId", stuId);
		
		return (ArrayList)sqlSession.selectList("studentMapper.showHomeworkList", paramMap);
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

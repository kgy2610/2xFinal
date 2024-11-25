package com.twoX.agit.student.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

}

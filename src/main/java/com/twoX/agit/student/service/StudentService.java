package com.twoX.agit.student.service;

import java.util.ArrayList;
import java.util.Map;

import com.twoX.agit.after.vo.AfterSchoolStudent;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.student.model.vo.HomeworkSubmit;

public interface StudentService {

	// 학생 - 숙제 리스트
	ArrayList<HomeworkSubmit> showHomeworkList(String classCode, String stuId);
	
	//방과후
		int updateAfterschoolCode(Map<String, String> map);

		String afterschoolCode(String stuId);

		AfterSchoolStudent afterschoolStart(String stuId);

		AfterSchool afterschool(String code);

		String afterschoolTeacher(String tcId);

}

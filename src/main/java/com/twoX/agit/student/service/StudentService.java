package com.twoX.agit.student.service;

import java.util.ArrayList;

import com.twoX.agit.student.model.vo.HomeworkSubmit;

public interface StudentService {

	// 학생 - 숙제 리스트
	ArrayList<HomeworkSubmit> showHomeworkList(String classCode, String stuId);

}

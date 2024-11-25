package com.twoX.agit.teacher.service;

import java.util.ArrayList;

import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Teacher;

public interface TeacherService {
	
	// 회원정보 수정
	int updateInfo(Teacher t);

	// 반 삭제
	int classDelete(Teacher t);

	// 코드 중복 확인
	AfterSchool getAfterClassByTeacherId(String tcId);

	// 방과후 반 개설
	int makeAfterClass(AfterSchool as);
	
	// 숙제 전체조회
	int getListCount();

	// 숙제 페이지 조회
	ArrayList<Homework> getAllHomework();

	// 숙제 등록
	int enrollHomework(String tcId, String classCode, String title, String subject, String content, String dueDate);

	// 숙제 조회
	Homework selectHomework(int bno);

	// 숙제 삭제
	int deleteHomework(String hmTitle);

	// 숙제 수정
	int updateHomework(String hmTitle, String subject, String deadLine, String hmContent);
}
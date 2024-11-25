package com.twoX.agit.manager.Service;

import java.util.ArrayList;

import com.twoX.agit.member.model.vo.School;
import com.twoX.agit.member.model.vo.Teacher;

public interface ManagerService {
	//SC_CODE로 학교 이름 조회
	School getSchoolNameByScCode(String scCode);

	//전체 교직원 수 
	int getTeacherCount();
	
	//현재 학생 수
	int getStudentCountBySchoolCode(String scCode);

	//승인 완료 교직원 리스트
	ArrayList<Teacher> getActiveTeachersByScCode(String scCode);
	
	//승인 필요 교직원 리스트
	ArrayList<Teacher> ajaxAcceptTeacherListbyScCode(String scCode);

	//교직원 삭제
	int deleteTeacherByTcIdAndScCode(String tcId, String scCode);

	//교직원 승인
	int RequestTeacherByTcIdAndStatus(String tcId, String status, String scCode);
}

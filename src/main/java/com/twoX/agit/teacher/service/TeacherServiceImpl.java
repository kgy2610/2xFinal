package com.twoX.agit.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.vo.AfterSchoolBoard;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;
import com.twoX.agit.teacher.model.dao.TeacherDao;

@Service
public class TeacherServiceImpl  implements TeacherService{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public int updateInfo(Teacher t) {
		return teacherDao.updateInfo(sqlSession, t);
	}
	
	@Override
	public int classDelete(Teacher t) {
		return teacherDao.classDelete(sqlSession,t);
	}


	@Override
	public AfterSchool getAfterClassByTeacherId(String tcId) {
		return teacherDao.getAfterClassByTeacherId(sqlSession, tcId);
	}
	
	@Override
	public int makeAfterClass(AfterSchool as) {
		return teacherDao.makeAfterClass(sqlSession, as);
	}
	
	// 숙제 페이징
	@Override
	public int getListCount() {
		return teacherDao.selectListCount(sqlSession);
	}

	// 숙제 전체조회
	@Override
	public ArrayList<Homework> getAllHomework(String tcId) {
		return teacherDao.selectHomeworkList(sqlSession, tcId);
	}

	// 숙제 등록
	@Override
	public int enrollHomework(String tcId, String classCode, String title, String subject, String content,
			String dueDate) {
		return teacherDao.enrollHomework(sqlSession, tcId, classCode, title, subject, content, dueDate);
	}
	
	// 가장 최근 숙제 조회
	public int getRecentHomeworkBoNo(String tcId) { // 가장 최근 등록된 BO_NO 가져오기
		return teacherDao.selectRecentHomeworkBoNo(sqlSession, tcId);
	}
	
	// 파일 업로드
	@Override
	public int uploadHomeworkFile(int boNo, String originName, String changeName) {
		return teacherDao.uploadHomeworkFile(sqlSession, boNo, originName, changeName);
	}

	// 숙제 조회
	@Override
	public Homework selectHomework(int bno) {
		return teacherDao.selectHomework(sqlSession, bno);
	}
	
	// 파일 삭제
	@Override
	public int deleteFile(int boNo, String changeName) {
		return teacherDao.deleteFile(sqlSession, boNo, changeName);
	}

	// 숙제 삭제
	@Override
	public int deleteHomework(String hmTitle) {
		return teacherDao.deleteHomework(sqlSession, hmTitle);
	}

	// 숙제 수정
	@Override
	public int updateHomework(String hmTitle, String subject, String deadLine, String hmContent) {
		return teacherDao.updateHomework(sqlSession, hmTitle, subject, deadLine, hmContent);
	}
	
	// 숙제 파일 수정
	@Override
	public int updateHomeworkFile(int boNo, String originName, String newFileName, String changeName) {
		return teacherDao.updateHomeworkFile(sqlSession, boNo, originName, newFileName, changeName);
	}

	// 해당 숙제 페이지로 이동
	@Override
	public ArrayList<Homework> selectSubject( String classCode) {
		return (ArrayList)teacherDao.selectSubject(sqlSession,classCode);
	}
	
	// 숙제 상세 페이지
	@Override
	public ArrayList<HmSubmit> gosubmitHomework(String title, String studentId) {
		return (ArrayList)teacherDao.selectsubmitHomework(sqlSession, title, studentId);
	}
	
	// 숙제 점수 및 말씀 부여
	@Override
	public int updateSubmitHomework(String teacherComment, int score, String stuId) {
		return teacherDao.updateSubmitHomework(sqlSession, teacherComment, score, stuId);
	}

	// 오늘자 출결 확인
	@Override
	public ArrayList<Attendance> selectAttendanceList(String aDate) {
		return teacherDao.selectAttendanceList(sqlSession, aDate);
	}

	// 출결 저장
	@Override
	public int insertAttendance(List<Attendance> attandanceList) {
		return teacherDao.insertAttendance(sqlSession, attandanceList);
	}

	// 출결 상태 업데이트
	@Override
	public int updateAttendance(ArrayList<Attendance> updateAttendance) {
		return teacherDao.updateAttendance(sqlSession, updateAttendance);
	}

	@Override
	public List<Map<String, Object>> smCodeStudent(String code) {
		return teacherDao.smCodeStudent(sqlSession, code);
	}

	@Override
	 public List<Map<String, Object>> getStudentScoresByStuId(String stuId) {
        return teacherDao.getStudentScoresByStuId(sqlSession, stuId);
    }
	
	@Override
    public String udStudentStatus(String classCode, String stuId) {
        // 예시: classCode에 해당하는 학생들의 상태를 업데이트하는 로직
        int updatedCount = teacherDao.udStudentStatus(sqlSession, classCode, stuId);
        
        if (updatedCount > 0) {
            return "학생 상태가 성공적으로 업데이트되었습니다.";
        } else {
            return "학생 상태 업데이트에 실패하였습니다.";
        }
    }

	@Override
	public ArrayList<Student> inStudentListbyScCode(String cCode) {
		return teacherDao.stuActiveList(sqlSession, "sqlSession", cCode);
	}

	@Override
	public int inStudentListbyStatus( String status, String scCode, String stuId) {
		return teacherDao.inStudentListbyStatus(sqlSession,status , scCode, stuId);
	}
	
	
}
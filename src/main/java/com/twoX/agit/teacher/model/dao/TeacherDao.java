package com.twoX.agit.teacher.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.member.model.vo.AfterSchool;
import com.twoX.agit.member.model.vo.Attendance;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.member.model.vo.Student;
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
	public ArrayList<Homework> selectHomeworkList(SqlSessionTemplate sqlSession, String tcId) {
		return (ArrayList) sqlSession.selectList("homeworkMapper.selectAllList", tcId);
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

	// 해당 숙제 페이지로 이동
	public ArrayList<Homework> selectSubject(SqlSessionTemplate sqlSession, String classCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("classCode", classCode);
		return (ArrayList)sqlSession.selectList("boardMapper.selectSubject", params);
	}
	
	// 숙제 점수 및 말씀 부여
	public int updateSubmitHomework(SqlSessionTemplate sqlSession, String teacherComment, int score, String stuId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teacherComment", teacherComment);
		params.put("score", score);
		params.put("stuId", stuId);
		
		return sqlSession.update("boardMapper.updateSubject", params);
	}
	
	public ArrayList<HmSubmit> selectsubmitHomework(SqlSessionTemplate sqlSession, String title, String studentId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("studentId", studentId);
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectsubmitHomework", params);
	}
	
	
	public int insertAttendance(SqlSessionTemplate sqlSession, List<Attendance> attendanceList) {
		return sqlSession.insert("attendanceMapper.insertAttendance", attendanceList);
	}
	

	public int updateAttendance(SqlSessionTemplate sqlSession, ArrayList<Attendance> updateAttendance) {
		return sqlSession.update("attendanceMapper.updateAttendance", updateAttendance);
	}
	
	public List<Map<String, Object>> smCodeStudent(SqlSessionTemplate sqlSession, String code){	
		return (ArrayList)sqlSession.selectList("stuManageMapper.smCodeStudent", code);
	}
	
	public List<Map<String, Object>> getStudentScoresByStuId(SqlSessionTemplate sqlSession, String stuId) {
        return sqlSession.selectList("stuManageMapper.getStudentScoresByStuId", stuId);
    }
	
	
}
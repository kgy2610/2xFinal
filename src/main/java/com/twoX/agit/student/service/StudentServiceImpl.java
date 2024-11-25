package com.twoX.agit.student.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Homework;
import com.twoX.agit.student.model.dao.StudentDao;

@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private StudentDao studentDao;
	
	// 학생 숙제 리스트 갯수 조회
	@Override
	public int selectListCount(String classCode, String stuId) {
		return studentDao.selectListCount(sqlSession, classCode, stuId);
	}

	// 학생 숙제 리스트 가져오기
	@Override
	public ArrayList<Homework> selectStudentHomeworkList(String classCode, String stuId, PageInfo pi) {
		return studentDao.selectStudentHomeworkList(sqlSession, classCode, stuId, pi);
	}

	// 학생 숙제 번호에 맞는 게시물 조회
	@Override
	public Homework selectNowHomework(int boNo) {
		return studentDao.selectNowHomework(sqlSession, boNo);
	}
	
	// 게시글 번호로 숙제 조회
	@Override
    public Homework getHomeworkByBoNo(int boNo) {
        return studentDao.selectHomeworkByBoNo(sqlSession, boNo);
    }

	// 숙제 제출
    @Override
    public int submitHomework(HmSubmit hm) {
        return studentDao.insertHomeworkSubmission(sqlSession, hm);
    }
}


package com.twoX.agit.board.service;

import java.util.ArrayList;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.board.model.dao.BoardDao;
import com.twoX.agit.board.model.vo.Counsel;
import com.twoX.agit.board.model.vo.EventImgBoard;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.board.model.vo.Notice;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.board.model.vo.Reply;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Teacher;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardDao boardDao;
	
	//공지사항 가져오기
	@Override
	public ArrayList<Notice> selectNoticeList(Notice n) {
		return boardDao.selectNoticeList(sqlSession,n);
	}
	
	//학생 점수 가져오기
	@Override
	public ArrayList<Double> selectscore(String stuId) {
		return boardDao.selectscore(sqlSession,stuId);
	}
	
	//교육청코드 가져오기
	@Override
	public String selectOeCode(String schoolCode) {
		return boardDao.selectOeCode(sqlSession,schoolCode);
	}

	//학급 평균 점수 가져오기
	@Override
	public ArrayList<Double> selectAVGscore(String classCode) {
		return boardDao.selectAVGscore(sqlSession,classCode);
	}

	//학생 과목별 점수 가져오기
	@Override
	public ArrayList<HmSubmit> selectStudentScoreList(Map params) {
		return boardDao.selectStudentScoreList(sqlSession,params);
	}
	
	// 부모님 상담가능 시간 가져오기
	@Override
	public ArrayList<Counsel> selectSameMonthCounsel(Map params) {
		return boardDao.selectSameMonthCounsel(sqlSession, params);
	}

	// 부모님 상담 신청하기
	@Override
	public int updateCounsel(Counsel c) {
		return boardDao.updateCounsel(sqlSession, c);
	}

	//부모님 커뮤니티 게시물 갯수 조회
	@Override
	public int selectListCount(String classCode) {
		return boardDao.selectListCount(sqlSession,classCode);
	}
	
	//부모님 커뮤니티 게시물 가져오기
	@Override
	public ArrayList<ParentsBoard> selectParentsBoardList(String classCode,PageInfo pi) {
		return boardDao.selectParentsBoardList(sqlSession,classCode,pi);
	}
	
	//부모님 커뮤니티 게시물 작성
	@Override
	public int insertParentBoard(ParentsBoard pb) {
		return boardDao.insertParentBoard(sqlSession,pb);
	}
	
	//부모님 커뮤니티 게시물 수정
	@Override
	public int updateParentBoard(ParentsBoard pb) {
		return boardDao.updateParentBoard(sqlSession,pb);
	}
	
	//부모님 커뮤니티 게시물 삭제
	@Override
	public int deleteParentBoard(int boNo) {
		return boardDao.deleteParentBoard(sqlSession,boNo);
	}
	
	//부모님 커뮤니티 게시물 조회수 증가
	@Override
	public int updateCount(int boNo) {
		return boardDao.updateCount(sqlSession,boNo);
	}
	
	//부모님 커뮤니티 번호에 맞는 게시물 조회
	@Override
	public ParentsBoard selectNowBoard(int boNo) {
		return boardDao.selectNowBoard(sqlSession,boNo);
	}
	
	//부모님 커뮤니티 번호에 맞는 댓글 리스트 조회
	@Override
	public ArrayList<Reply> selectReplyList(int boNo) {
		return boardDao.selectReplyList(sqlSession,boNo);
	}
	
	
	//부모님 커뮤니티 메인화면 게시물 조회
	@Override
	public ArrayList<ParentsBoard> selectHotBoardList(String classCode) {
		return boardDao.selectHotBoardList(sqlSession,classCode);
	}
	
	//부모님 행사사진 게시물 전체 조회
	@Override
	public ArrayList<EventImgBoard> selectEventImgList(Teacher t) {
		return boardDao.selectEventImgList(sqlSession,t);
	}
	
	//부모님 행사사진 조회
	@Override
	public EventImgBoard selectDetailImg(int boNo) {
		return boardDao.selectDetailImg(sqlSession,boNo);
	}

	//출석상태
	@Override
	public String selectNowStatus(String stuId) {
		return boardDao.selectNowStatus(sqlSession,stuId);
	}
	
	//출석률
	@Override
	public Map selectRate(String stuId) {
		return boardDao.selectRate(sqlSession,stuId);
	}
	
	//댓글 작성
	@Override
	public int insertReply(Reply r) {
		return boardDao.insertReply(sqlSession,r);
	}

	//댓글 삭제
	@Override
	public int deleteReply(int reNo) {
		return boardDao.deleteReply(sqlSession,reNo);
	}

	//댓글 수정
	@Override
	public int modifyReply(Reply r) {
		return boardDao.modifyReply(sqlSession,r);
	}
	
	//댓글 수
	@Override
	public int selectReCount(int boNo) {
		return boardDao.selectReCount(sqlSession,boNo);
	}
	
	
	
	//------------------------------------------------------------------------
	//선생님 행사사진 삭제
	@Override
	public int deleteImgBoard(int boNo) {
		return boardDao.deleteImgBoard(sqlSession,boNo);
	}
	
	//선생님 행사사진 작성
	@Override
	public int insertIMGBoard(EventImgBoard eib) {
		return boardDao.insertIMGBoard(sqlSession,eib);
	}

	//선생님 행사사진 수정
	@Override
	public int updateImgBoard(EventImgBoard eib) {
		return boardDao.updateImgBoard(sqlSession,eib);
	}
	
	
	



}

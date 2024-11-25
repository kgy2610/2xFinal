package com.twoX.agit.board.model.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.twoX.agit.board.model.vo.Counsel;
import com.twoX.agit.board.model.vo.EventImgBoard;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.board.model.vo.Notice;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.board.model.vo.Reply;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Teacher;

@Repository
public class BoardDao {
	//공지사항리스트
	public ArrayList<Notice> selectNoticeList(SqlSessionTemplate sqlSession, Notice n) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectNoticeList", n);
	}
	
	//교육청코드 가져오기
	public String selectOeCode(SqlSessionTemplate sqlSession, String schoolCode) {
		return sqlSession.selectOne("schoolMapper.selectOeCode", schoolCode);
	}
	
	//학생점수 가져오기
	public ArrayList<Double> selectscore(SqlSessionTemplate sqlSession, String stuId) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectscore", stuId);
	}
	
	//출석률
	public Map selectRate(SqlSessionTemplate sqlSession, String stuId) {
		return sqlSession.selectOne("boardMapper.selectRate", stuId);
	}
	
	//출석상태
	public String selectNowStatus(SqlSessionTemplate sqlSession, String stuId) {
		return sqlSession.selectOne("boardMapper.selectNowStatus", stuId);
	}
	
	//전체학생평균점수 가져오기
	public ArrayList<Double> selectAVGscore(SqlSessionTemplate sqlSession, String classCode) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectAVGscore", classCode);
	}
	
	//학생 과목별 상세점수 가져오기
	public ArrayList<HmSubmit> selectStudentScoreList(SqlSessionTemplate sqlSession, Map params) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectStudentScoreList", params);
	}
	
	// 부모님 상담가능 시간 가져오기
	public ArrayList<Counsel> selectSameMonthCounsel(SqlSessionTemplate sqlSession, Map params) {
		return (ArrayList) sqlSession.selectList("boardMapper.selectSameMonthCounsel", params);
	}

	// 부모님 상담 신청하기
	public int updateCounsel(SqlSessionTemplate sqlSession, Counsel c) {
		return sqlSession.update("boardMapper.updateCounsel", c);
	}
	
	//부모님 커뮤니티 게시물 갯수 조회
	public int selectListCount(SqlSessionTemplate sqlSession, String classCode) {
		return sqlSession.selectOne("boardMapper.selectListCount", classCode);
	}
	
	//부모님 커뮤니티 게시물 가져오기
	public ArrayList<ParentsBoard> selectParentsBoardList(SqlSessionTemplate sqlSession, String classCode,PageInfo pi) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("boardMapper.selectParentsBoardList", classCode,rowBounds);
	}
	
	//부모님 커뮤니티 게시물 작성
	public int insertParentBoard(SqlSessionTemplate sqlSession, ParentsBoard pb) {
		return sqlSession.insert("boardMapper.insertParentBoard", pb);
	}
	
	//부모님 커뮤니티 게시물 수정
	public int updateParentBoard(SqlSessionTemplate sqlSession, ParentsBoard pb) {
		return sqlSession.update("boardMapper.updateParentBoard", pb);
	}
	
	//부모님 커뮤니티 게시물 삭제
	public int deleteParentBoard(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.delete("boardMapper.deleteParentBoard", boNo);
	}
	
	//부모님 커뮤니티 게시물 조회수 증가
	public int updateCount(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.update("boardMapper.updateCount", boNo);
	}
	
	//부모님 커뮤니티 번호에 맞는 게시물 조회
	public ParentsBoard selectNowBoard(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.selectOne("boardMapper.selectNowBoard", boNo);
	}
	
	//부모님 커뮤니티 번호에 맞는 댓글 리스트 조회
	public ArrayList<Reply> selectReplyList(SqlSessionTemplate sqlSession, int boNo) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectReplyList", boNo);
	}
	
	//댓글작성
	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("boardMapper.insertReply", r);
	}
	
	//댓글삭제
	public int deleteReply(SqlSessionTemplate sqlSession, int reNo) {
		return sqlSession.delete("boardMapper.deleteReply", reNo);
	}
	
	//댓글수정
	public int modifyReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("boardMapper.modifyReply", r);
	}
	
	//댓글 수
	public int selectReCount(SqlSessionTemplate sqlSession,int boNo) {
		return sqlSession.selectOne("boardMapper.selectReCount", boNo);
	}
	
	
	//부모님 커뮤니티 메인화면 게시물 조회
	public ArrayList<ParentsBoard> selectHotBoardList(SqlSessionTemplate sqlSession, String classCode) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectHotBoardList", classCode);
	}
	
	//부모님 행사사진 게시물 전체 조회
	public ArrayList<EventImgBoard> selectEventImgList(SqlSessionTemplate sqlSession, Teacher t) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectEventImgList", t);
	}
	
	//부모님 행사사진 조회
	public EventImgBoard selectDetailImg(SqlSessionTemplate sqlSession, int boNo) {
		return sqlSession.selectOne("boardMapper.selectDetailImg", boNo);
	}
	
	
	//--------------------------------------------------------------------------------------------------
	public int deleteImgBoard(SqlSessionTemplate sqlSession,int boNo) {
		return sqlSession.delete("boardMapper.deleteImgBoard",boNo);
	}
	
	public int insertIMGBoard(SqlSessionTemplate sqlSession, EventImgBoard eib) {
		return sqlSession.insert("boardMapper.insertIMGBoard",eib);
	}
	
	public int updateImgBoard(SqlSessionTemplate sqlSession, EventImgBoard eib) {
		return sqlSession.update("boardMapper.updateImgBoard",eib);
	}
	
}
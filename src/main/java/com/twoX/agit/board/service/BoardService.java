package com.twoX.agit.board.service;

import java.util.ArrayList;
import java.util.Map;

import com.twoX.agit.board.model.vo.Counsel;
import com.twoX.agit.board.model.vo.EventImgBoard;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.board.model.vo.Notice;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.board.model.vo.Reply;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Teacher;

public interface BoardService {
	//공지사항 가져오기
	ArrayList<Notice> selectNoticeList(Notice n);
	
	//교육청코드 가져오기
	String selectOeCode(String schoolCode);
	
	//출석률
	Map selectRate(String stuId);
	
	//출석상태
	String selectNowStatus(String stuId);
	
	//학생 점수 가져오기
	ArrayList<Double> selectscore(String stuId);
	
	//학급 평균 점수 가져오기
	ArrayList<Double> selectAVGscore(String classCode);
	
	//학생 과목별 점수 가져오기
	ArrayList<HmSubmit> selectStudentScoreList(Map params);
	
	//부모님 상담가능 시간 가져오기
	ArrayList<Counsel> selectSameMonthCounsel(Map params);
	
	//부모님 상담신청
	int updateCounsel(Counsel c);
		
	//부모님 커뮤니티 게시물 갯수 조회
	int selectListCount(String classCode);
	
	//부모님 커뮤니티 댓글 수 조회
	int selectReCount(int boNo);
	
	//부모님 커뮤니티 게시물 가져오기
	ArrayList<ParentsBoard> selectParentsBoardList(String classCode,PageInfo pi);
	
	//부모님 커뮤니티 게시물 작성
	int insertParentBoard(ParentsBoard pb);
	
	//부모님 커뮤니티 수정
	int updateParentBoard(ParentsBoard pb);
	
	//부모님 커뮤니티 삭제
	int deleteParentBoard(int boNo);
	
	//부모님 커뮤니티 게시물 조회수 증가
	int updateCount(int boNo);
	
	//부모님 커뮤니티 번호에 맞는 게시물 조회
	ParentsBoard selectNowBoard(int boNo);
	
	//부모님 커뮤니티 번호에 맞는 댓글 리스트 조회
	ArrayList<Reply> selectReplyList(int boNo);
	
	//댓글 작성
	int insertReply(Reply r);
	
	//댓글 삭제
	int deleteReply(int reNo);
	
	//댓글 수정
	int modifyReply(Reply r);
	
	//부모님 커뮤니티 메인화면 게시물 조회
	ArrayList<ParentsBoard> selectHotBoardList(String classCode);
	
	//부모님 행사사진 게시물 전체 조회
	ArrayList<EventImgBoard> selectEventImgList(Teacher t);
	
	//부모님 행사사진 조회
	EventImgBoard selectDetailImg(int boNo);
	
	
	
	//---------------------------------------------------------------------------------
	//선생님 행사사진 삭제
	int deleteImgBoard(int boNo);
	
	//
	int insertIMGBoard(EventImgBoard eib);
	
	int updateImgBoard(EventImgBoard eib);
}  
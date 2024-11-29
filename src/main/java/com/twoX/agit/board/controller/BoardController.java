package com.twoX.agit.board.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.twoX.agit.board.model.vo.Counsel;
import com.twoX.agit.board.model.vo.EventImgBoard;
import com.twoX.agit.board.model.vo.HmSubmit;
import com.twoX.agit.board.model.vo.Notice;
import com.twoX.agit.board.model.vo.ParentsBoard;
import com.twoX.agit.board.model.vo.Reply;
import com.twoX.agit.board.service.BoardService;
import com.twoX.agit.common.template.Template;
import com.twoX.agit.common.vo.PageInfo;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;

@CrossOrigin
@Controller
public class BoardController {
	private final BoardService boardService;
	public static final String SERVICE_KEY = "f72f3af259f9408d88585329410824e5";
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	//공지사항 가져오기
	@ResponseBody
	@RequestMapping(value="notice.list", produces="application/json; charset-UTF-8")
	public String ajaxSelectNoticeList(HttpSession session) {
		Teacher t = (Teacher)session.getAttribute("teacher");
		Notice n = new Notice(t.getTcId(),t.getClassCode(),null,null);
		ArrayList<Notice> list = boardService.selectNoticeList(n);
		return new Gson().toJson(list);
	}
	
	  //급식api 
	  @ResponseBody
	  @GetMapping(value="BoMealService", produces="application/json; charset=UTF-8")
	  public String getMealInfo(HttpSession session) throws IOException {
		 Teacher t = (Teacher)session.getAttribute("teacher");
		 System.out.println(t);
		 String schoolCode = t.getClassCode().substring(0,7);
		 String oecode = boardService.selectOeCode(schoolCode);
	     String url = "https://open.neis.go.kr/hub/mealServiceDietInfo";
	     LocalDate today = LocalDate.now();
	        // 날짜를 특정 형식으로 포맷
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	     String formattedDate = today.format(formatter);
	     LocalDate tomorrow = today.plusDays(1);
	     String tomorrowString = tomorrow.format(formatter);
	     url += "?KEY=" + SERVICE_KEY;
	     url += "&type=json";
	     url += "&pIndex=1";
	     url += "&pSize=100";
	     url += "&ATPT_OFCDC_SC_CODE=" + URLEncoder.encode(oecode, "UTF-8" );
	     url += "&SD_SCHUL_CODE=" + URLEncoder.encode(schoolCode, "UTF-8");
	     url += "&MLSV_FROM_YMD=" + URLEncoder.encode(formattedDate, "UTF-8");
	     url += "&MLSV_TO_YMD=" + URLEncoder.encode(tomorrowString, "UTF-8");
	     	     
	     //1. 요청하고자하는 url을 전달하면서 java.net.URL객체 생성
	     URL requestURL = new URL(url);
	
	     //2. 만들어진 URL 객체를 가지고 HttpURLConnection객체를 생성
	     HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
	
	     //3. 요청에 필요한 Header정보 설정하기 POST로 보낼꺼면 POST적기
	     urlConnection.setRequestMethod("GET");
	
	     //4. 해당 api서버로 요청 보낸 후 입력데이터 읽어오기
	     BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	
	     String result = "";
	     String line;
	     while((line = br.readLine()) != null ) {
	        result += line;
	     }
	
	     br.close();
	     urlConnection.disconnect();
	     	     
	     return result;
	     
	  }
	//성적페이지 
	@RequestMapping("parents_score")
	public String parents_score(HttpSession session) {
		Student s = (Student)session.getAttribute("child");
//		if(s.getClassCode()==null ) {
//			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
//			return "parents/parents_myPage";
//		}
		ArrayList<Double> list = new ArrayList();
		list = boardService.selectscore(s.getStuId());
		if (list.size() < 7) {
		    while (list.size() < 7) {
		        list.add(0.0);
		    }
		}
		ArrayList<Double> alist = new ArrayList();
		alist = boardService.selectAVGscore(s.getClassCode());
		if (alist.size() < 7) {
		    while (alist.size() < 7) {
		        alist.add(0.0);
		    }
		}
		for (int i = 0; i <list.size(); i++) {
			list.set(i,Math.round(list.get(i)*10.0)/10.0);
			alist.set(i,Math.round(alist.get(i)*10.0)/10.0);
		}
		
		session.setAttribute("stu_score_list", list);
		session.setAttribute("stu_AVG_score_list", alist);	
		return "parents/parents_score";
	}
	
	//성적페이지 과목별 점수
	@ResponseBody
	@RequestMapping(value="score.list", produces="application/json; charset-UTF-8")
	public String ajaxSelectReplyList(String subject,HttpSession session) {
		Parents p = (Parents)session.getAttribute("loginUser");
		Map<String, String> params = new HashMap();
		params.put("prId", p.getPrId());
		params.put("subject", subject);
		ArrayList<HmSubmit> list = boardService.selectStudentScoreList(params);
		return new Gson().toJson(list);
	}
	
	//부모님 마이페이지로 이동
	@RequestMapping("parents_mypage")
	public String parents_mypage(HttpSession session) {
		Student s = (Student)session.getAttribute("child");
		return "parents/parents_myPage";
	}
	
	//출석률, 출석상태
	@ResponseBody
	@GetMapping(value="searchNowStatus", produces="application/json; charset=UTF-8")
	public String searchNowStatus(HttpSession session) {
		Student s = (Student)session.getAttribute("child");
		Map rate = boardService.selectRate(s.getStuId());
		String nowStatus = boardService.selectNowStatus(s.getStuId());
		Map<String,String> result = new HashMap();
	    if (nowStatus != null) {
	        if (nowStatus.equals("AT")) {
	            result.put("status", "출석");
	        } else if (nowStatus.equals("TA")) {
	            result.put("status", "지각");
	        } else {
	            result.put("status", "결석");
	        }
	    } else {
	        result.put("status", "출석");
	    }
	    if(rate.get("TOTAL_COUNT")==null||rate.get("LA_AT_COUNT")==null) {
	    	result.put("rate","0%");
	    }else {
	    	Double total_count = ((BigDecimal)rate.get("TOTAL_COUNT")).doubleValue();
		    Double la_at_count = ((BigDecimal)rate.get("LA_AT_COUNT")).doubleValue();
		    result.put("rate",Math.round((la_at_count/total_count)*100)+"%");
	    }
		return new Gson().toJson(result);
	}
	
	// 부모님 커뮤니티페이지로 이동
	@RequestMapping("parents_community")
	public String parents_community(HttpSession session, Model model) {
		Student s = (Student) session.getAttribute("child");
		if (s.getClassCode() == null) {
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			return "parents/parents_myPage";
		}
		ArrayList<ParentsBoard> HotBoard = boardService.selectHotBoardList(s.getClassCode());
		if (HotBoard.size() < 4) {
			return "redirect:/all_community";
		}
		for(ParentsBoard pb : HotBoard) {
	        pb.setReCount(boardService.selectReCount(pb.getBoNo()));
			String html = pb.getContents();
			 // HTML 파싱
	        Document doc = Jsoup.parse(html);
	        // 첫 번째 이미지의 src 추출
	        String imgSrc = doc.select("img").attr("src");
	        if(imgSrc.equals("")) {
	        	continue;
	        }else {
	        	pb.setThumbnail(imgSrc);
	        }	        
		}
		model.addAttribute("HotBoard", HotBoard);
		return "parents/parents_Maincommunity";
	}

	// 부모님 상담페이지로 이동
	@RequestMapping("parents_calendar")
	public String parents_calendar(HttpSession session) {
		return "parents/parents_calendar";
	}

	// 부모님 상담 가능시간 가져오기
	@ResponseBody
	@RequestMapping(value = "selectSameMonthCounsel", produces = "application/json; charset-UTF-8")
	public String selectSameMonthCounsel(String month, HttpSession session) {
		Teacher t = (Teacher) session.getAttribute("teacher");
		Map<String, String> params = new HashMap();
		params.put("tcId", t.getTcId());
		params.put("month", month);
		ArrayList<Counsel> list = boardService.selectSameMonthCounsel(params);
		return new Gson().toJson(list);
	}

	// 부모님 상담 신청
	@RequestMapping("updateCounsel")
	public String updateCounsel(Counsel c, HttpSession session) {
		Parents p = (Parents) session.getAttribute("loginUser");
		c.setPrId(p.getPrId());
		int result = boardService.updateCounsel(c);
		if (result > 0) {
			session.setAttribute("alertMsg", "신청 완료되었습니다.");
		} else {
			session.setAttribute("alertMsg", "신청 실패하였습니다.");
		}
		return "redirect:/parents_calendar";
	}
	
	//부모님 행사사진페이지로 이동
	@RequestMapping("parents_eventImgList")
	public String parents_eventImgList(HttpSession session,Model model) {
		Student s = (Student)session.getAttribute("child");
		if(s.getClassCode()==null ) {
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			return "parents/parents_myPage";
		}
		Teacher t = (Teacher)session.getAttribute("teacher");
		ArrayList<EventImgBoard> list = boardService.selectEventImgList(t);
		for (EventImgBoard eib : list) {
			String html = eib.getBoContent();
			 // HTML 파싱
	        Document doc = Jsoup.parse(html);
	        // 첫 번째 이미지의 src 추출
	        String imgSrc = doc.select("img").attr("src");
	        eib.setThumbnail(imgSrc);
		}
		model.addAttribute("ImgList",list);
		return "parents/parents_eventImgList";
	}
	
	//부모님 행사사진 게시글 조회
	@RequestMapping("eventImgList_detail")
	public String eventImgList_detail(HttpSession session,Model model,int boNo) {
		EventImgBoard eib = boardService.selectDetailImg(boNo);
		model.addAttribute("eib",eib);
		return "parents/parents_eventImgDetail";
	}
	
	//부모님 게시물작성 페이지로 이동
	@RequestMapping("enroll_community")
	public String enroll_community() {
		return "parents/parents_enrollcommunity";
	}
	
	//부모님 커뮤니티 전체 글 보기
	@RequestMapping("all_community")
	public String all_community(@RequestParam(value="cpage", defaultValue="1") int currentPage,HttpSession session,Model model) {
		Student s = (Student)session.getAttribute("child");
		int boardCount = boardService.selectListCount(s.getClassCode());
		PageInfo pi = Template.getPageInfo(boardCount, currentPage, 5, 9);
		ArrayList<ParentsBoard> list = boardService.selectParentsBoardList(s.getClassCode(),pi);
		session.setAttribute("boardList", list);
		model.addAttribute("pi", pi);
		return "parents/parents_Allcommunity";
	}
	
	//부모님 게시물작성
	@RequestMapping("enroll_board")
	public String enroll_board(ParentsBoard pb, MultipartFile upfile, HttpSession session) {
		Student s = (Student)session.getAttribute("child");
		Parents p = (Parents)session.getAttribute("loginUser");
		if(!upfile.getOriginalFilename().equals("")) {
			String changeName = Template.saveFile(upfile, session, "/resources/file/");
			
			pb.setChangeName("/resources/file/" + changeName);
			pb.setOriginName(upfile.getOriginalFilename());
		}
		pb.setPrNickname(p.getPrId());
		pb.setClassCode(s.getClassCode());
		int result = boardService.insertParentBoard(pb);
		if(result>0) {
			return "redirect:/all_community";
		}else {
			session.setAttribute("alertMsg","게시글작성실패" );
			return "parents/parents_myPage";
		}	
	}
	
	//부모님 게시물 조회
	@RequestMapping("board_detail")
	public String board_detail(@RequestParam(value="boNo")int boNo,@RequestParam(value="cpage", defaultValue="1") int currentPage,HttpSession session,Model model) {
		int result = boardService.updateCount(boNo);
		if(result>0) {
			ParentsBoard npage = boardService.selectNowBoard(boNo);
			model.addAttribute("npage",npage);
			model.addAttribute("cpage",currentPage);
			return "parents/parents_detailcommunity";
		}else {
			session.setAttribute("alertMsg","게시글조회실패" );
			return "parents/parents_myPage";
		}
	}
	
	//댓글 조회
	@ResponseBody
	@RequestMapping(value = "selectReply", produces = "application/json; charset-UTF-8")
	public String selectReply(String boNo,HttpSession session,Model model) {
		ArrayList<Reply> rlist = boardService.selectReplyList(Integer.parseInt(boNo));
		session.setAttribute("rlist", rlist);
		return new Gson().toJson(rlist);
	}
	
	//댓글 작성
	@ResponseBody
	@RequestMapping(value = "insertReply")
	public int insertReply(Reply r,HttpSession session,Model model) {
		r.setPrId(((Parents)session.getAttribute("loginUser")).getPrId());
		int result = boardService.insertReply(r);
		return result;
	}
	
	//댓글 삭제
	@ResponseBody
	@RequestMapping(value = "deleteReply")
	public int deleteReply(String reNo,HttpSession session,Model model) {
		ArrayList<Reply> rlist = (ArrayList)session.getAttribute("rlist");
		for (Reply r : rlist) {
			if(r.getPrepNo()==Integer.parseInt(reNo)) {
				Reply mr = new Reply();
				mr.setReNo(Integer.parseInt(reNo));
				mr.setReContent("삭제된 댓글입니다.");
				int result = boardService.modifyReply(mr);
				return result;
			}
		}
		int result = boardService.deleteReply(Integer.parseInt(reNo));
		return result;
	}
	
	//댓글 수정
	@ResponseBody
	@RequestMapping(value = "modifyReply")
	public int modifyReply(Reply r,HttpSession session,Model model) {
		int result = boardService.modifyReply(r);
		return result;
	}
	
	//부모님 게시물 수정페이지로 이동
	@RequestMapping("board_modify")
	public String board_modify(@RequestParam(value="boNo")int boNo,@RequestParam(value="cpage", defaultValue="1") int currentPage,HttpSession session,Model model) {
		ParentsBoard npage = boardService.selectNowBoard(boNo);
		session.setAttribute("npage",npage);
		session.setAttribute("cupage",currentPage);
		return "parents/parents_modifycommunity";
	}
	
	//부모님 게시물 수정하기
	@RequestMapping("modifyParentsBoard")
	public String modifyParentsBoard(ParentsBoard pb,MultipartFile reupfile,HttpSession session) {
		if(!reupfile.getOriginalFilename().equals("")) {
			//기존 첨부파일이 있다 -> 기존파일을 삭제
			if(pb.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(pb.getChangeName())).delete();
			}
			
			//새로운 첨부파일을 서버에 업로드하기
			String changeName = Template.saveFile(reupfile, session, "/resources/file/");
			
			pb.setOriginName(reupfile.getOriginalFilename());
			pb.setChangeName( "/resources/file/" + changeName);
		}else {
			ParentsBoard opb = (ParentsBoard)session.getAttribute("npage");
			pb.setOriginName(opb.getOriginName());
			pb.setChangeName(opb.getChangeName());
		}
		int result = boardService.updateParentBoard(pb);
		if(result > 0) {
			session.setAttribute("alertMsg","수정 완료되었습니다.");
			return "redirect:/board_detail?boNo="+pb.getBoNo()+"&cpage="+(Integer)session.getAttribute("cupage");
		}else {
			session.setAttribute("alertMsg","수정 실패하였습니다.");
			return "redirect:/all_community";
		}
		
	}
	
	//부모님 게시물 삭제하기
	@RequestMapping("deleteParentsBoard")
	public String deleteParentsBoard(int boNo,HttpSession session) {
		int result = boardService.deleteParentBoard(boNo);
		if(result > 0) {
			session.setAttribute("alertMsg", "삭제 완료되었습니다.");
			return "redirect:/all_community?cpage="+(Integer)session.getAttribute("cupage"); 
		}else {
			session.setAttribute("alertMsg", "삭제 실패하였습니다.");
			return "redirect:/all_community";
		}	
	}
	
	// 커뮤니티 사진올릴때
	@ResponseBody
	@PostMapping("upload")
	public String upload(List<MultipartFile> fileList, HttpSession session) {
		
		List<String> changeNameList = new ArrayList();
		for(MultipartFile f : fileList) {
			changeNameList.add(Template.saveFile(f, session, "/resources/img/board/"));
		}
		return new Gson().toJson(changeNameList);
	}
	
	
	//------------------------------------------------------------------------------------------------
	@RequestMapping("teacher_eventImgList")
	public String teacher_eventImgList(HttpSession session,Model model) {
		Teacher t = (Teacher)session.getAttribute("loginUser");
		ArrayList<EventImgBoard> list = boardService.selectEventImgList(t);
		for (EventImgBoard eib : list) {
			String html = eib.getBoContent();
			 // HTML 파싱
	        Document doc = Jsoup.parse(html);
	        // 첫 번째 이미지의 src 추출
	        String imgSrc = doc.select("img").attr("src");
	        eib.setThumbnail(imgSrc);
		}
		model.addAttribute("ImgList",list);
		return "teacher/teacher_eventImgList";
	}
	
	//선생님 행사사진 게시글 조회
	@RequestMapping("teacher_eventImgList_detail")
	public String teacher_eventImgList_detail(HttpSession session,Model model,int boNo) {
		EventImgBoard eib = boardService.selectDetailImg(boNo);
		model.addAttribute("eib",eib);
		return "teacher/teacher_eventImgDetail";
	}
	
	
	//선생님 행사사진 수정페이지로 이동
	@RequestMapping("teacher_eventImgList_modify")
	public String teacher_eventImgList_modify(@RequestParam(value="boNo")int boNo,HttpSession session,Model model) {
		EventImgBoard npage = boardService.selectDetailImg(boNo);
		session.setAttribute("npage",npage);
		return "teacher/teacher_modifyEventImg";
	}
	
	//선생님 행사사진 작성페이지로 이동
	@RequestMapping("teacher_eventImgList_enroll")
	public String teacher_eventImgList_enroll() {
		return "teacher/teacher_enrollEventImg";
	}
	
	//선생님 행사사진 삭제
	@RequestMapping("deleteImgBoard")
	public String deleteImgBoard(@RequestParam(value="boNo")int boNo,HttpSession session,Model model) {
		int result = boardService.deleteImgBoard(boNo);
		if(result>0) {
			session.setAttribute("alertMsg", "삭제되었습니다.");
		}else {
			session.setAttribute("alertMsg", "삭제실패하였습니다..");
		}
		return "redirect:/teacher_eventImgList";
	}
	
	//선생님 행사사진 작성
	@RequestMapping("enroll_IMG")
	public String enroll_IMG(EventImgBoard eib, HttpSession session) {
		Teacher t = (Teacher)session.getAttribute("loginUser");
		eib.setTcId(t.getTcId());
		eib.setClassCode(t.getClassCode());
		System.out.println(eib);
		int result = boardService.insertIMGBoard(eib);
		if(result>0) {
			return "redirect:/teacher_eventImgList";
		}else {
			session.setAttribute("alertMsg","게시글작성실패" );
			return "parents/parents_myPage";
		}	
	}
	
	//선생님 행사사진 수정하기
	@RequestMapping("modifyImgBoard")
	public String modifyImgBoard(EventImgBoard eib,HttpSession session) {
		int result = boardService.updateImgBoard(eib);
		if(result > 0) {
			session.setAttribute("alertMsg","수정 완료되었습니다.");
			return "redirect:/teacher_eventImgList_detail?boNo="+eib.getBoNo();
		}else {
			session.setAttribute("alertMsg","수정 실패하였습니다.");
			return "redirect:/all_community";
		}
		
	}
	
	//선생님 상담일정
	@RequestMapping("teacherCounsel")
	public String teacherCounsel() {
		return "teacher/teacher_Counsel";
	}
}
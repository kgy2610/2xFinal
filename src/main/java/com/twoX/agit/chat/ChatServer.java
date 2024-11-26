package com.twoX.agit.chat;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twoX.agit.member.model.vo.Parents;
import com.twoX.agit.member.model.vo.Student;
import com.twoX.agit.member.model.vo.Teacher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatServer extends TextWebSocketHandler{
	
	private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

	//클라이언트가 연결을 맺을 때 호출이되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String Id;
		if(session.getAttributes().get("loginUser") instanceof Student) {
			Id = ((Student)session.getAttributes().get("loginUser")).getStuId();
		}else if(session.getAttributes().get("loginUser") instanceof Teacher){
			Id = ((Teacher)session.getAttributes().get("loginUser")).getTcId();
		}else {
			Id = ((Parents)session.getAttributes().get("loginUser")).getPrId();
		}
		System.out.println(Id);
		log.info("{} 연결됨...", Id);
		
		userSessions.put(Id, session);
	}

	//클라이언트로부터 메세지를 받을 때 호출되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//세션에 저장되어있는 발송자 닉네임 가져오기
		String Id;
		if(session.getAttributes().get("loginUser") instanceof Student) {
			Id = ((Student)session.getAttributes().get("loginUser")).getStuId();
		}else if(session.getAttributes().get("loginUser") instanceof Teacher){
			Id = ((Teacher)session.getAttributes().get("loginUser")).getTcId();
		}else {
			Id = ((Parents)session.getAttributes().get("loginUser")).getPrId();
		}
		
		//메세지 payload를 json객체로 파싱
		JsonObject obj = new JsonParser().parse(message.getPayload()).getAsJsonObject();
		
		log.info("message : {}", obj.get("message").getAsString());
		log.info("target : {}", obj.get("target").getAsString());
		
		Chat vo = new Chat();
		vo.setChContent(obj.get("message").getAsString()); // 메세지
		if(session.getAttributes().get("loginUser") instanceof Student) {
			vo.setStuId(Id);
		}else if(session.getAttributes().get("loginUser") instanceof Teacher){
			vo.setTcId(Id);
		}
		//vo.setTargetNick(obj.get("target").getAsString()); //수신자
		//vo.setTime(new Date().toLocaleString());
		
		//sendMEssageUser(vo);
	}
	
//	//특정 사용자에게 메세지를 전송하는 메소드
//	private void sendMEssageUser(Chat vo) {
//		
//		WebSocketSession mySession = userSessions.get(vo.getNick());
//		//수신자 세션정보 가져오기
//		WebSocketSession targetSession = userSessions.get(vo.getTargetNick());
	
//		if(targetSession != null && targetSession.isOpen()) {
//			String str = new Gson().toJson(vo);
//			//웹소켓 텍스트 전송규격 메세지로 변환
//			TextMessage msg = new TextMessage(str);
//			//log.info("msg : {}",vo.getMsg());
//			try {
//				targetSession.sendMessage(msg);
//				mySession.sendMessage(msg);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	//클라이언트가 연결을 끊을 때 호출되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}

}

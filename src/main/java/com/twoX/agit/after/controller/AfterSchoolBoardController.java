package com.twoX.agit.after.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.twoX.agit.after.service.AfterSchoolBoardService;
import com.twoX.agit.member.service.MemberService;


@CrossOrigin
@Controller
public class AfterSchoolBoardController {
	 private final AfterSchoolBoardService afterSchoolBoardService;
	 
	 @Autowired
	   public AfterSchoolBoardController(AfterSchoolBoardService afterSchoolBoardService) {
	      this.afterSchoolBoardService = afterSchoolBoardService;
	   }
	 
	 
}
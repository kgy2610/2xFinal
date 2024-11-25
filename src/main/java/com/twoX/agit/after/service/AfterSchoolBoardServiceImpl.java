
package com.twoX.agit.after.service;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twoX.agit.after.dao.AfterSchoolBoardDao;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AfterSchoolBoardServiceImpl implements AfterSchoolBoardService{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private AfterSchoolBoardDao afterSchoolBoardDao;
	
	
	
	
	
}

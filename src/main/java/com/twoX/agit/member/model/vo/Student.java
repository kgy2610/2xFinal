package com.twoX.agit.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Student {
	private String stuId;
	private String stuPwd;
	private String classCode;
	private String stuName;
	private String phone;
	private String stuNum;
	private String status;
	private String picNo;
	private String stuQuestion;
	private String stuAnswer;
}
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
public class Teacher {
	private String tcId;
	private String classCode;
	private String tcPwd;
	private String scCode;
	private String tcName;
	private String phone;
	private String status;
	private String tcQuestion;
	private String tcAnswer;
}

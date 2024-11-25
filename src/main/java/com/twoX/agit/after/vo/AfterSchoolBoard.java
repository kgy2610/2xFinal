package com.twoX.agit.after.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AfterSchoolBoard {
	private int boNo;
	private String stuId;
	private String code;
	private String title;
	private String boContent;
	private Date createDate;
	private String originName;
	private String changeName;
}

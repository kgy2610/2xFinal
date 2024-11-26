package com.twoX.agit.board.model.vo;

import java.sql.Date;

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
public class HmSubmit {
	private String stuId;
	private String classCode;
	private int boNo;
	private String hmTitle;
	private String hmStuContent;
	private int score;
	private String tcComment;
	private String createDate;
	private String status;
}
package com.twoX.agit.board.model.vo;

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
	private int boNo;
	private String hmTitle;
	private String hmContent;
	private int score;
	private String tcComment;
	private String createDate;
	private String status;
}
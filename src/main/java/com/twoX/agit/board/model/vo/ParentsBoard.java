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
public class ParentsBoard {
	private String prNickname;
	private String prId;
	private int boNo;
	private String contents;
	private String classCode;
	private String boCount;
	private String boTitle;
	private String createDate;
	private String thumbnail;
	private String originName;
	private String changeName;
	private int reCount;
}

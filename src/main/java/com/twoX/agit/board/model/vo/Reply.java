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
public class Reply {
	private int reNo;
	private int boNo;
	private String prId;
	private String reContent;
	private String nickname;
	private int prepNo;
}

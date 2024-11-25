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
public class EventImgBoard {
	private int boNo;
	private String tcId;
	private String classCode;
	private String title;
	private String createDate;
	private String boContent;
	private String thumbnail;
}


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
public class Counsel {
	private int csNo;
	private String csDate;
	private String tcId;
	private String prId;
	private String csLocation;
	private String csContent;
	private String prName;
}
